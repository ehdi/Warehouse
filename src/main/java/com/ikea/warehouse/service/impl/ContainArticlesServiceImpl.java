package com.ikea.warehouse.service.impl;

import com.ikea.warehouse.domain.ContainArticles;
import com.ikea.warehouse.repository.ContainArticlesRepository;
import com.ikea.warehouse.service.ContainArticlesService;
import com.ikea.warehouse.service.dto.ContainArticlesDTO;
import com.ikea.warehouse.service.mapper.ContainArticlesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ContainArticlesServiceImpl implements ContainArticlesService {

  private final Logger log = LoggerFactory.getLogger(ContainArticlesServiceImpl.class);

  private final ContainArticlesMapper containArticlesMapper;
  private final ContainArticlesRepository containArticlesRepository;

  public ContainArticlesServiceImpl(
      ContainArticlesMapper containArticlesMapper,
      ContainArticlesRepository containArticlesRepository) {
    this.containArticlesMapper = containArticlesMapper;
    this.containArticlesRepository = containArticlesRepository;
  }

  @Override
  public ContainArticlesDTO save(ContainArticlesDTO containArticlesDTO) {
    log.debug("Request to save contain article : {}", containArticlesDTO);
    ContainArticles savedArticle = containArticlesRepository.save(
        containArticlesMapper.toEntity(containArticlesDTO)
    );
    return containArticlesMapper.toDTO(savedArticle);
  }
}
