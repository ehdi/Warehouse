package com.ika.warehouseinventory.controller;

import com.ika.warehouseinventory.service.InventoryService;
import com.ika.warehouseinventory.service.dto.InventoryDTO;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/inventory")
public class InventoryController {

  private final InventoryService inventoryService;

  public InventoryController(InventoryService inventoryService) {
    this.inventoryService = inventoryService;
  }

  /**
   * List of items in inventory
   *
   * @param page page number
   * @param size number of items in each page
   * @return list of inventory
   */
  @GetMapping("/all")
  public ResponseEntity<List<InventoryDTO>> getAllInventory(
      @RequestParam(defaultValue = "0") Integer page,
      @RequestParam(defaultValue = "10") Integer size) {

    Pageable paging = PageRequest.of(page, size);
    return ResponseEntity
        .ok(inventoryService.findAll(paging));
  }



}
