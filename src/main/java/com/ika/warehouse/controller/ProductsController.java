package com.ika.warehouse.controller;

import com.ika.warehouse.service.ProductsService;
import com.ika.warehouse.service.dto.ProductsDTO;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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


  /**
   * Delete a product and update the inventory
   *
   * @param name the product name that you want to delete
   * @return OK
   */
  @DeleteMapping("/remove/{name}")
  public ResponseEntity removeProduct(@PathVariable String name) {
    productsService.remove(name);
    return ResponseEntity
        .ok(HttpStatus.OK);
  }

}
