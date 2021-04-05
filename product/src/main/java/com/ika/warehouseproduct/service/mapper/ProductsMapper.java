package com.ika.warehouseproduct.service.mapper;

import com.ika.warehouseproduct.domain.Products;
import com.ika.warehouseproduct.service.dto.ProductsDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductsMapper extends EntityMapper<ProductsDTO, Products> {
}
