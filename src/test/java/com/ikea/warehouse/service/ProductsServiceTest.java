package com.ikea.warehouse.service;

import com.ikea.warehouse.service.dto.ProductsDTO;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith({SpringExtension.class})
@SpringBootTest
public class ProductsServiceTest {

  @Autowired
  private ProductsService productsService;

  @Test
  public void fetchAllProducts_ShouldReturnTotalNumber() {
    List<ProductsDTO> allProducts = productsService.getAllProducts();
    Assertions.assertEquals(6, allProducts.size());
  }


}
