package com.ika.warehouseproduct.service;

import com.ika.warehouseproduct.service.dto.InventoryDTO;
import java.util.List;

public interface InventoryService {
  List<InventoryDTO> getListOfItemsInInventory(Integer page, Integer size);
}
