package com.ikea.warehouse.service;

import com.ikea.warehouse.service.dto.InventoryDTO;
import java.util.List;
import java.util.Optional;

public interface InventoryService {

  Optional<InventoryDTO> findByArtID(Long artId);
  InventoryDTO save(InventoryDTO inventoryDTO);
  InventoryDTO update(InventoryDTO inventoryDTO);
  List<InventoryDTO> findAll();
  Optional<InventoryDTO> findById(Long id);

}
