package com.ikea.warehouse.controller;

import com.ikea.warehouse.service.ProductsService;
import com.ikea.warehouse.service.dto.ProductsDTO;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/products")
public class ProductsController {

  private final ProductsService productsService;

  public ProductsController(ProductsService productsService) {
    this.productsService = productsService;
  }

  /**
   * List of Products based on the quantity of inventory
   *
   * @return list of products
   */
  @GetMapping("/all")
  public ResponseEntity<Map<String, List<ProductsDTO>>> getAllProducts() {
    return ResponseEntity
        .ok(productsService.getProductByInventoryQuantity());
  }

}
