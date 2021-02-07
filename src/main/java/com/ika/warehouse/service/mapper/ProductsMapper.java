package com.ika.warehouse.service.mapper;

import com.ika.warehouse.domain.Products;
import com.ika.warehouse.service.dto.ProductsDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ContainArticlesMapper.class})
public interface ProductsMapper extends EntityMapper<ProductsDTO, Products> {
}
