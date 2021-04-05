package com.ika.warehouseinventory.service.mapper;

import com.ika.warehouseinventory.domain.Inventory;
import com.ika.warehouseinventory.service.dto.InventoryDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InventoryMapper extends EntityMapper<InventoryDTO, Inventory> {
}
