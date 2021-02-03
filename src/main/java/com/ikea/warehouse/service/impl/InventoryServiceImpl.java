package com.ikea.warehouse.service.impl;

import com.ikea.warehouse.domain.Inventory;
import com.ikea.warehouse.exception.ItemNotFoundException;
import com.ikea.warehouse.repository.InventoryRepository;
import com.ikea.warehouse.service.InventoryService;
import com.ikea.warehouse.service.dto.InventoryDTO;
import com.ikea.warehouse.service.mapper.InventoryMapper;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class InventoryServiceImpl implements InventoryService {

  private final Logger log = LoggerFactory.getLogger(InventoryServiceImpl.class);

  private final InventoryRepository inventoryRepository;
  private final InventoryMapper inventoryMapper;

  public InventoryServiceImpl(InventoryRepository inventoryRepository,
      InventoryMapper inventoryMapper) {
    this.inventoryRepository = inventoryRepository;
    this.inventoryMapper = inventoryMapper;
  }

  @Override
  public InventoryDTO save(InventoryDTO inventoryDTO) {
    log.debug("Request to save an item in inventory : {}", inventoryDTO);
    Inventory savedItem = inventoryRepository.save(
        inventoryMapper.toEntity(inventoryDTO)
    );
    return inventoryMapper.toDTO(savedItem);
  }

  @Override
  public InventoryDTO update(InventoryDTO inventoryDTO) {
    log.debug("Request to update an item in inventory : {}", inventoryDTO);
    return inventoryRepository.findById(inventoryDTO.getArtId())
        .map(inventoryItem -> {
          inventoryItem.setName(inventoryDTO.getName());
          inventoryItem.setStock(inventoryDTO.getStock());
          return inventoryItem;
        })
        .map(inventoryMapper::toDTO)
        .orElseThrow(ItemNotFoundException::new);
  }

  @Override
  public List<InventoryDTO> findAll() {
    log.debug("Request to fet all item of inventory");
    return inventoryMapper.toDTO(inventoryRepository.findAll());
  }

  @Override
  public Optional<InventoryDTO> findById(Long id) {
    log.debug("Request to fet all item of inventory");
    return inventoryRepository.findById(id).map(inventoryMapper::toDTO);
  }

}
