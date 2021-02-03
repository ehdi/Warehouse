package com.ikea.warehouse.controller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import capital.scalable.restdocs.AutoDocumentation;
import capital.scalable.restdocs.jackson.JacksonResultHandlers;
import capital.scalable.restdocs.response.ResponseModifyingPreprocessors;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ikea.warehouse.service.ContainArticlesService;
import com.ikea.warehouse.service.InventoryService;
import com.ikea.warehouse.service.ProductsService;
import com.ikea.warehouse.service.dto.ProductsDTO;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.cli.CliDocumentation;
import org.springframework.restdocs.http.HttpDocumentation;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
public class ProductsControllerIntegrationTest {

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  @Value("${server.port}")
  private Integer port;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private InventoryService inventoryService;

  @Autowired
  private ProductsService productsService;

  @Autowired
  private ContainArticlesService containArticlesService;

  private MockMvc mockMvc;

  @BeforeEach
  public void setUp(
      WebApplicationContext webApplicationContext,
      RestDocumentationContextProvider restDocumentation) {

    this.mockMvc =
        MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .apply(documentationConfiguration(restDocumentation))
            .alwaysDo(JacksonResultHandlers.prepareJackson(objectMapper))
            .alwaysDo(
                document(
                    "{method-name}",
                    Preprocessors.preprocessRequest(),
                    Preprocessors.preprocessResponse(
                        ResponseModifyingPreprocessors.replaceBinaryContent(),
                        ResponseModifyingPreprocessors.limitJsonArrayLength(objectMapper),
                        Preprocessors.prettyPrint())))
            .apply(
                MockMvcRestDocumentation.documentationConfiguration(restDocumentation)
                    .uris()
                    .withScheme("http")
                    .withHost("localhost")
                    .withPort(port)
                    .and()
                    .snippets()
                    .withDefaults(
                        CliDocumentation.curlRequest(),
                        HttpDocumentation.httpRequest(),
                        HttpDocumentation.httpResponse(),
                        AutoDocumentation.requestFields(),
                        AutoDocumentation.responseFields(),
                        AutoDocumentation.pathParameters(),
                        AutoDocumentation.requestParameters(),
                        AutoDocumentation.description(),
                        AutoDocumentation.methodAndPath(),
                        AutoDocumentation.section()))
            .build();
  }


  @Test
  public void productsFetchAll_ShouldReturnListOfProducts() throws Exception {
    MvcResult mvcResult = this.mockMvc.perform(
        get("/v1/products/all"))
        .andDo(print())
        .andExpect(status().isOk())
        .andReturn();

    Map<String, List<ProductsDTO>> response =
        OBJECT_MAPPER.readValue(mvcResult.getResponse().getContentAsString(),
        new TypeReference<>() {});

    Assertions.assertEquals(2, response.size());
    for (Map.Entry<String, List<ProductsDTO>> entry : response.entrySet()) {
      Assertions.assertEquals(3, entry.getValue().size());
    }

    for (Map.Entry<String, List<ProductsDTO>> entry : response.entrySet()) {
      List<ProductsDTO> productsDTOS = (List<ProductsDTO>) entry.getValue();
      Assertions.assertEquals(3, entry.getValue().size());
      Assertions.assertEquals(1,productsDTOS.get(1).getContainArticlesList().size());
    }
  }

}
