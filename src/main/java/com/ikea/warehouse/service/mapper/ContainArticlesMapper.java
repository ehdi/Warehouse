package com.ikea.warehouse.service.mapper;

import com.ikea.warehouse.domain.ContainArticles;
import com.ikea.warehouse.service.dto.ContainArticlesDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ContainArticlesMapper extends EntityMapper<ContainArticlesDTO, ContainArticles> {

}
