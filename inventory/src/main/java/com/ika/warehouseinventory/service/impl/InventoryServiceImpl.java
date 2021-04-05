package com.ika.warehouseinventory.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ika.warehouseinventory.domain.Inventory;
import com.ika.warehouseinventory.exception.ItemNotFoundException;
import com.ika.warehouseinventory.repository.InventoryRepository;
import com.ika.warehouseinventory.service.InventoryService;
import com.ika.warehouseinventory.service.dto.InventoryDTO;
import com.ika.warehouseinventory.service.dto.ProductsDTO;
import com.ika.warehouseinventory.service.mapper.InventoryMapper;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class InventoryServiceImpl implements InventoryService {

  private final Logger log = LoggerFactory.getLogger(InventoryServiceImpl.class);

  private final InventoryRepository inventoryRepository;
  private final InventoryMapper inventoryMapper;
  private final ObjectMapper objectMapper;

  public InventoryServiceImpl(InventoryRepository inventoryRepository,
      InventoryMapper inventoryMapper, ObjectMapper objectMapper) {
    this.inventoryRepository = inventoryRepository;
    this.inventoryMapper = inventoryMapper;
    this.objectMapper = objectMapper;
  }

  @Override
  public Optional<InventoryDTO> findByArtID(Long artId) {
    log.debug("Request to find an item by art id : {}", artId);
    return inventoryRepository.findById(artId)
        .map(inventoryMapper::toDTO);
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
          inventoryItem.setStock(inventoryDTO.getStock());
          inventoryRepository.save(inventoryItem);
          return inventoryItem;
        })
        .map(inventoryMapper::toDTO)
        .orElseThrow(ItemNotFoundException::new);
  }

  @Override
  public List<InventoryDTO> findAll(Pageable pageable) {
    log.debug("Request to fet all item of inventory");
    return inventoryMapper.toDTO(inventoryRepository.findAll(pageable).toList());
  }

  @Override
  public Optional<InventoryDTO> findById(Long id) {
    log.debug("Request to fet all item of inventory");
    return inventoryRepository.findByArtId(id).map(inventoryMapper::toDTO);
  }

  @Override
  @RabbitListener(queues = "inventoryQueue")
  public void updateInventoryByAmq(String income) {
    log.info("Receive message form inventory queue - {}", income);
    try {
      ProductsDTO productsDTO = objectMapper.readValue(income, ProductsDTO.class);
      productsDTO.getContainArticlesList()
          .forEach(item ->{
            InventoryDTO inventoryDTO = this.findByArtID(item.getArtId())
                .orElseThrow(ItemNotFoundException::new);

            inventoryDTO.setStock(inventoryDTO.getStock() - item.getAmountOf());
            this.save(inventoryDTO);
          });
    } catch (JsonProcessingException e) {
      log.error(e.getMessage());
    }
  }

}
