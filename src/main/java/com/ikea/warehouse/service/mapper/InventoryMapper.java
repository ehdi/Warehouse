package com.ikea.warehouse.service.mapper;

import com.ikea.warehouse.domain.Inventory;
import com.ikea.warehouse.service.dto.InventoryDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InventoryMapper extends EntityMapper<InventoryDTO, Inventory> {
}
