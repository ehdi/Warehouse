package com.ikea.warehouse.service.mapper;

import com.ikea.warehouse.domain.Products;
import com.ikea.warehouse.service.dto.ProductsDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ContainArticlesMapper.class})
public interface ProductsMapper extends EntityMapper<ProductsDTO, Products> {
}
