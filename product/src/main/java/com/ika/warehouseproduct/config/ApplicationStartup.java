package com.ika.warehouseproduct.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ika.warehouseproduct.service.ProductsService;
import com.ika.warehouseproduct.service.dto.ContainArticlesDTO;
import com.ika.warehouseproduct.service.dto.ProductsDTO;
import com.ika.warehouseproduct.service.dto.ProductsListDTO;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartup {

  private final Logger log = LoggerFactory.getLogger(ApplicationStartup.class);

  private final Environment environment;
  private final ProductsService productsService;

  public ApplicationStartup(Environment environment,
      ProductsService productsService) {
    this.environment = environment;
    this.productsService = productsService;
  }

  @PostConstruct
  void postConstruct(){
    if(checkTestProfile()) {
      log.info("Initiate DB");
      loadProductsData();
    }
  }

  public void loadProductsData(){
    ObjectMapper mapper = new ObjectMapper();
    TypeReference<ProductsListDTO> typeReference = new TypeReference<>(){};
    InputStream inputStream =
        TypeReference.class.getResourceAsStream("/data/products.json");
    try {

      ProductsListDTO productsListDTO = mapper.readValue(inputStream,typeReference);

      productsListDTO.getProducts().forEach(productsJson -> {
        ProductsDTO productsDTO = new ProductsDTO();
        productsDTO.setName(productsJson.getName());
        productsDTO.setRawName(
            StringUtils
            .deleteWhitespace(productsJson.getName())
            .toLowerCase()
        );

        List<ContainArticlesDTO> containArticlesDTOList = new ArrayList<>();
        productsJson.getContainArticlesList().forEach(articleJson -> {
          ContainArticlesDTO containArticlesDTO = new ContainArticlesDTO();
          containArticlesDTO.setArtId(articleJson.getArtId());
          containArticlesDTO.setAmountOf(articleJson.getAmountOf());
          containArticlesDTOList.add(containArticlesDTO);
        });

        productsDTO.setContainArticlesList(containArticlesDTOList);
        productsService.save(productsDTO);
      });

    } catch (IOException e){
      System.out.println("Unable to save products: " + e.getMessage());
    }
  }

  private boolean checkTestProfile() {
    String[] activeProfiles = environment.getActiveProfiles();
    for (String activeProfile : activeProfiles) {
      if (activeProfile.contains("test"))
        return false;
    }
    return true;
  }

}
