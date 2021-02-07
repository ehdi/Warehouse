package com.ika.warehouse.service.mapper;

import com.ika.warehouse.domain.ContainArticles;
import com.ika.warehouse.service.dto.ContainArticlesDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ContainArticlesMapper extends EntityMapper<ContainArticlesDTO, ContainArticles> {

  @Mapping(target = "product", ignore = true)
  ContainArticlesDTO toDTO(ContainArticles source);

}
