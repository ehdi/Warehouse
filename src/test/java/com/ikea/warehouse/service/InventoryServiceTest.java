package com.ikea.warehouse.service;

import com.ikea.warehouse.config.ApplicationStartup;
import com.ikea.warehouse.exception.ItemNotFoundException;
import com.ikea.warehouse.repository.InventoryRepository;
import com.ikea.warehouse.service.dto.InventoryDTO;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith({SpringExtension.class})
@SpringBootTest
@DirtiesContext
public class InventoryServiceTest {

  @Autowired
  private InventoryService inventoryService;

  @Autowired
  private InventoryRepository inventoryRepository;

  @Autowired
  private ApplicationStartup applicationStartup;

  @BeforeEach
  public void setUp(){
    applicationStartup.loadInventoryData();
    applicationStartup.loadProductsData();
  }

  @AfterAll
  public void deleteProductsFromDB() throws InterruptedException {
    inventoryRepository.deleteAll();
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

  @Test
  public void findByArtIDInventory_ShouldReturnOneItem() {
    Assertions.assertEquals(true, inventoryService.findByArtID(1L).isPresent());
  }

}
