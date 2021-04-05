package com.ika.warehouseproduct.controller;

import com.ika.warehouseproduct.service.ProductsService;
import com.ika.warehouseproduct.service.dto.ProductsDTO;
import com.ika.warehouseproduct.service.dto.ResponseMessageDTO;
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
  public ResponseEntity<List<ProductsDTO>> getAllProducts() {
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
  public ResponseEntity<ResponseMessageDTO> removeProduct(@PathVariable String name) {
    productsService.remove(name);
    return ResponseEntity
        .ok(new ResponseMessageDTO(HttpStatus.OK,"Deleted"));
  }

}
