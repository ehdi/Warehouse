package com.ikea.warehouse.service;

import com.ikea.warehouse.domain.Products;
import com.ikea.warehouse.service.dto.ProductsDTO;
import java.util.List;

public interface ProductsService {

  List<Products> getAllProducts();
  ProductsDTO save(ProductsDTO productsDTO);

}
