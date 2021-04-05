package com.ika.warehouseinventory.service;

import com.ika.warehouseinventory.config.ApplicationStartup;
import com.ika.warehouseinventory.exception.ItemNotFoundException;
import com.ika.warehouseinventory.service.dto.InventoryDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
  private ApplicationStartup applicationStartup;

  @BeforeEach
  public void setUp(){
    applicationStartup.loadInventoryData();
  }

  @Test
  public void fetchAllInventory_ShouldReturnTotalNumber() throws Exception {
    Pageable paging = PageRequest.of(0, 4);
    Assertions.assertEquals(4, inventoryService.findAll(paging).size());
  }

  @Test
  public void updateInventory_ShouldReturnUpdatedInventory() throws Exception {
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
  public void findByArtIDInventory_ShouldReturnOneItem() throws Exception {
    Assertions.assertEquals(true, inventoryService.findByArtID(1L).isPresent());
  }

}
