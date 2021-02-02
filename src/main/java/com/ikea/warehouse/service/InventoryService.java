package com.ikea.warehouse.service;

import com.ikea.warehouse.domain.Inventory;
import com.ikea.warehouse.service.dto.InventoryDTO;

public interface InventoryService {

  InventoryDTO save(InventoryDTO inventoryDTO);
  InventoryDTO update(InventoryDTO inventoryDTO);

}
