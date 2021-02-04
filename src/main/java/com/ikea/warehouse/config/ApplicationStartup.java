package com.ikea.warehouse.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ikea.warehouse.domain.Inventory;
import com.ikea.warehouse.service.ContainArticlesService;
import com.ikea.warehouse.service.InventoryService;
import com.ikea.warehouse.service.dto.ContainArticlesDTO;
import com.ikea.warehouse.service.dto.InventoryListDTO;
import com.ikea.warehouse.service.dto.ProductsDTO;
import com.ikea.warehouse.service.dto.ProductsListDTO;
import java.io.IOException;
import java.io.InputStream;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartup {

  private final Logger log = LoggerFactory.getLogger(ApplicationStartup.class);

  private final InventoryService inventoryService;
  private final ContainArticlesService containArticlesService;
  private final Environment environment;

  public ApplicationStartup(InventoryService inventoryService,
      ContainArticlesService containArticlesService,
      Environment environment) {
    this.inventoryService = inventoryService;
    this.containArticlesService = containArticlesService;
    this.environment = environment;
  }

  @PostConstruct
  void postConstruct(){
    if(checkTestProfile()) {
      log.info("Initiate DB");
      loadInventoryData();
      loadProductsData();
    }
  }

  public void loadInventoryData(){
    ObjectMapper mapper = new ObjectMapper();
    TypeReference<InventoryListDTO> typeReference = new TypeReference<>(){};
    InputStream inputStream =
        TypeReference.class.getResourceAsStream("/data/inventory.json");

    try {
      InventoryListDTO inventoriesDTO = mapper.readValue(inputStream,typeReference);
      inventoriesDTO.getInventory().forEach(inventoryJson -> {
        Inventory inventory = new Inventory();
        inventory.setName(inventoryJson.getName());
        inventory.setStock(inventoryJson.getStock());
        inventoryService.save(inventoryJson);
      });
    } catch (IOException e){
      System.out.println("Unable to save inventories: " + e.getMessage());
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

        productsJson.getContainArticlesList().forEach(articleJson -> {
          ContainArticlesDTO containArticlesDTO = new ContainArticlesDTO();
          containArticlesDTO.setArtId(articleJson.getArtId());
          containArticlesDTO.setAmountOf(articleJson.getAmountOf());

          containArticlesDTO.setProduct(productsDTO);
          containArticlesService.save(containArticlesDTO);
        });

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
