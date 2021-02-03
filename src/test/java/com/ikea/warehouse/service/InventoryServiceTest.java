package com.ikea.warehouse.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ikea.warehouse.domain.Inventory;
import com.ikea.warehouse.exception.ItemNotFoundException;
import com.ikea.warehouse.service.dto.InventoryDTO;
import com.ikea.warehouse.service.dto.InventoryListDTO;
import java.io.IOException;
import java.io.InputStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith({SpringExtension.class})
@SpringBootTest
public class InventoryServiceTest {

  @Autowired
  private InventoryService inventoryService;

  @BeforeEach
  public void setUp(){
    loadInventoryData();
  }

  private void loadInventoryData(){
    ObjectMapper mapper = new ObjectMapper();
    TypeReference<InventoryListDTO> typeReference = new TypeReference<>(){};
    InputStream inputStream = TypeReference.class.getResourceAsStream("/data/inventory.json");
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


  @Test
  public void fetchAllInventory_ShouldReturnTotalNumber() {
    Assertions.assertEquals(4, inventoryService.findAll().size());
  }

  @Test
  public void updateInventory_ShouldReturnUpdatedInventory() {
    inventoryService.findById(1L)
        .map(inventoryDTO -> {
          inventoryDTO.setStock(14L);
          inventoryService.save(inventoryDTO);
          return inventoryDTO;
        });

    InventoryDTO inventoryDTO = inventoryService.findById(1L)
        .orElseThrow(ItemNotFoundException::new);

    Assertions.assertEquals(14, inventoryDTO.getStock());
  }

}