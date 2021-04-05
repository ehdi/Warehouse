package com.ika.warehouseinventory.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ika.warehouseinventory.domain.Inventory;
import com.ika.warehouseinventory.service.InventoryService;
import com.ika.warehouseinventory.service.dto.InventoryListDTO;
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
  private final Environment environment;

  public ApplicationStartup(InventoryService inventoryService,
      Environment environment) {
    this.inventoryService = inventoryService;
    this.environment = environment;
  }

  @PostConstruct
  void postConstruct(){
    if(checkTestProfile()) {
      log.info("Initiate DB");
      loadInventoryData();
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

  private boolean checkTestProfile() {
    String[] activeProfiles = environment.getActiveProfiles();
    for (String activeProfile : activeProfiles) {
      if (activeProfile.contains("test"))
        return false;
    }
    return true;
  }

}
