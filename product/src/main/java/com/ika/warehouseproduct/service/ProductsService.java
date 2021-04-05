package com.ika.warehouseproduct.service;

import com.ika.warehouseproduct.service.dto.ProductsDTO;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.data.domain.Pageable;

public interface ProductsService {

  List<ProductsDTO> getAllProducts(Pageable pageable);
  ProductsDTO save(ProductsDTO productsDTO);
  Optional<ProductsDTO> findByName(String name);
  List<ProductsDTO> getProductByInventoryQuantity();
  void remove(String name);

}
