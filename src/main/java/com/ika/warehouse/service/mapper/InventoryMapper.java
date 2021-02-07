package com.ika.warehouse.service.mapper;

import com.ika.warehouse.domain.Inventory;
import com.ika.warehouse.service.dto.InventoryDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InventoryMapper extends EntityMapper<InventoryDTO, Inventory> {
}
