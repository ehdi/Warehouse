package com.ika.warehouseproduct.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ika.warehouseproduct.config.ApplicationStartup;
import com.ika.warehouseproduct.exception.ItemNotFoundException;
import com.ika.warehouseproduct.service.dto.ProductsDTO;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith({SpringExtension.class})
@SpringBootTest
@DirtiesContext
public class ProductsServiceTest {

  @Autowired
  private ProductsService productsService;

  @Autowired
  private ApplicationStartup applicationStartup;


  @BeforeEach
  public void setUp(){
    applicationStartup.loadProductsData();
  }

  @Test
  public void fetchAllProducts_ShouldReturnTotalNumber() throws Exception {
    Pageable paging = PageRequest.of(0, 2);
    List<ProductsDTO> allProducts = productsService.getAllProducts(paging);
    Assertions.assertEquals(2, allProducts.size());
  }

  @Test
  public void removeProduct_ShouldReturnTotalNumberAfterRemoveProduct() throws Exception {
    productsService.remove("diningchair");

    Pageable paging = PageRequest.of(0, 2);
    List<ProductsDTO> allProducts = productsService.getAllProducts(paging);
    Assertions.assertEquals(1, allProducts.size());
  }

  @Test
  public void removeProduct_ShouldReturnNotFound() throws Exception {
    Exception exception = assertThrows(ItemNotFoundException.class, () -> {
      productsService.remove("Dining Chair--X");
    });

    String expectedMessage = "Item not found";
    String actualMessage = exception.getMessage();

    assertTrue(actualMessage.contains(expectedMessage));
  }

}
