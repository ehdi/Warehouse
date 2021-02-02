package com.ikea.warehouse.service.impl;

import com.ikea.warehouse.repository.ProductsRepository;
import com.ikea.warehouse.service.ProductsService;
import com.ikea.warehouse.service.dto.ProductsDTO;
import com.ikea.warehouse.service.mapper.ProductsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductsServiceImpl implements ProductsService {

  private final Logger log = LoggerFactory.getLogger(ProductsServiceImpl.class);

  private final ProductsRepository productsRepository;
  private final ProductsMapper productsMapper;

  public ProductsServiceImpl(ProductsRepository productsRepository,
      ProductsMapper productsMapper) {
    this.productsRepository = productsRepository;
    this.productsMapper = productsMapper;
  }

  @Override
  public Page<ProductsDTO> getAllProducts(Pageable pageable) {
    log.debug("Request to fetch all products by : {}", pageable);
    return productsRepository.findAll(pageable)
        .map(productsMapper::toDTO);
  }

}
