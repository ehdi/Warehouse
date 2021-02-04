package com.ikea.warehouse.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ikea.warehouse.config.ApplicationStartup;
import com.ikea.warehouse.exception.ItemNotFoundException;
import com.ikea.warehouse.repository.ProductsRepository;
import com.ikea.warehouse.service.dto.ProductsDTO;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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

  @Autowired
  private ApplicationStartup applicationStartup;

  @Autowired
  private ProductsRepository productsRepository;

  @BeforeEach
  public void setUp(){
    applicationStartup.loadInventoryData();
    applicationStartup.loadProductsData();
  }

  @AfterEach
  public void deleteProductsFromDB() {
    productsRepository.deleteAll();
  }

  @Test
  public void fetchAllProducts_ShouldReturnTotalNumber() {
    List<ProductsDTO> allProducts = productsService.getAllProducts();
    Assertions.assertEquals(6, allProducts.size());
  }

  @Test
  public void removeProduct_ShouldReturnTotalNumberAfterRemoveProduct() {
    productsService.remove("Dining Chair");
    List<ProductsDTO> allProducts = productsService.getAllProducts();
    Assertions.assertEquals(3, allProducts.size());
  }

  @Test
  public void removeProduct_ShouldReturnNotFound() {
    Exception exception = assertThrows(ItemNotFoundException.class, () -> {
      productsService.remove("Dining Chair--X");
    });

    String expectedMessage = "Item not found";
    String actualMessage = exception.getMessage();

    assertTrue(actualMessage.contains(expectedMessage));
  }

}
