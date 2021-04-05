package com.ika.warehouseinventory.service;

import com.ika.warehouseinventory.service.dto.InventoryDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;

public interface InventoryService {

  Optional<InventoryDTO> findByArtID(Long artId);
  InventoryDTO save(InventoryDTO inventoryDTO);
  InventoryDTO update(InventoryDTO inventoryDTO);
  List<InventoryDTO> findAll(Pageable pageable);
  Optional<InventoryDTO> findById(Long id);
  void updateInventoryByAmq(String income);

}
