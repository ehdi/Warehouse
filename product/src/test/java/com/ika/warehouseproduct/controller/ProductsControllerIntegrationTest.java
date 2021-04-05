package com.ika.warehouseproduct.controller;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import capital.scalable.restdocs.AutoDocumentation;
import capital.scalable.restdocs.jackson.JacksonResultHandlers;
import capital.scalable.restdocs.response.ResponseModifyingPreprocessors;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ika.warehouseproduct.config.ApplicationStartup;
import com.ika.warehouseproduct.enumeration.ProductType;
import com.ika.warehouseproduct.service.InventoryService;
import com.ika.warehouseproduct.service.PricingService;
import com.ika.warehouseproduct.service.ProductsService;
import com.ika.warehouseproduct.service.dto.InventoryDTO;
import com.ika.warehouseproduct.service.dto.PriceCalculationDTO;
import com.ika.warehouseproduct.service.dto.PriceDTO;
import com.ika.warehouseproduct.service.dto.ProductsDTO;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.cli.CliDocumentation;
import org.springframework.restdocs.http.HttpDocumentation;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
@DirtiesContext
public class ProductsControllerIntegrationTest {

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  @Value("${server.port}")
  private Integer port;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private ProductsService productsService;

  @Autowired
  private ApplicationStartup applicationStartup;

  @MockBean
  private InventoryService inventoryService;

  @MockBean
  private PricingService pricingService;

  private MockMvc mockMvc;

  @BeforeAll
  public void init(){
    applicationStartup.loadProductsData();
  }

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
                        AutoDocumentation.pathParameters(),
                        AutoDocumentation.description(),
                        AutoDocumentation.methodAndPath(),
                        AutoDocumentation.section()))
            .build();
  }


  @Test
  public void productsFetchAll_ShouldReturnListOfProducts() throws Exception {

    List<InventoryDTO> inventoryDTOList = new ArrayList<>();
    inventoryDTOList.add(new InventoryDTO(1L,"leg",12L));
    inventoryDTOList.add(new InventoryDTO(2L,"screw",17L));
    inventoryDTOList.add(new InventoryDTO(3L,"seat",2L));
    inventoryDTOList.add(new InventoryDTO(4L,"table top",1L));
    when(inventoryService.getListOfItemsInInventory(0,10))
        .thenReturn(inventoryDTOList);

    PriceDTO priceDTO = new PriceDTO();
    priceDTO.setPrice(new BigDecimal(1200));
    when(pricingService.calculate(new PriceCalculationDTO(ProductType.DINING_CHAIR)))
        .thenReturn(priceDTO);

    MvcResult mvcResult = this.mockMvc.perform(
        get("/v1/products/all"))
        .andDo(print())
        .andExpect(status().isOk())
        .andReturn();

    List<ProductsDTO> response =
        OBJECT_MAPPER.readValue(mvcResult.getResponse().getContentAsString(),
        new TypeReference<>() {});

    Assertions.assertEquals(2, response.size());
    for (ProductsDTO entry : response) {
      Assertions.assertEquals(3, entry.getContainArticlesList().size());
    }

  }

  @Test
  public void productDelete_ShouldRemoveProduct() throws Exception {
    this.mockMvc.perform(
        delete("/v1/products/remove/dinningtable"))
        .andDo(print())
        .andExpect(status().isOk())
        .andReturn();

    Pageable paging = PageRequest.of(0, 2);
    List<ProductsDTO> allProducts = productsService.getAllProducts(paging);
    Assertions.assertEquals(1, allProducts.size());
  }

}
