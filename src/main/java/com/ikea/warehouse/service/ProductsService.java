package com.ikea.warehouse.service;

import com.ikea.warehouse.service.dto.ProductsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductsService {

  Page<ProductsDTO> getAllProducts(Pageable pageable);

}
