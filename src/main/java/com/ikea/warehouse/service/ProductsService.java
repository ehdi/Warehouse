package com.ikea.warehouse.service;

import com.ikea.warehouse.service.dto.ProductsDTO;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ProductsService {

  List<ProductsDTO> getAllProducts();
  ProductsDTO save(ProductsDTO productsDTO);
  Optional<ProductsDTO> findByName(String name);
  Map<String, List<ProductsDTO>> getProductByInventoryQuantity();
  void remove(String name);

}
