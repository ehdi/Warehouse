package com.ikea.warehouse.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ikea.warehouse.domain.Products;
import com.ikea.warehouse.service.dto.ContainArticlesDTO;
import com.ikea.warehouse.service.dto.ProductsDTO;
import com.ikea.warehouse.service.dto.ProductsListDTO;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
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
  private ContainArticlesService containArticlesService;

  @BeforeEach
  public void setUp(){
    loadProductsData();
  }

  private void loadProductsData(){
    ObjectMapper mapper = new ObjectMapper();
    TypeReference<ProductsListDTO> typeReference = new TypeReference<>(){};
    InputStream inputStream = TypeReference.class.getResourceAsStream("/data/products.json");
    try {

      ProductsListDTO productsListDTO = mapper.readValue(inputStream,typeReference);
      productsListDTO.getProducts().forEach(productsJson -> {

        ProductsDTO productsDTO = new ProductsDTO();
        productsDTO.setName(productsJson.getName());
        productsService.save(productsDTO);

        productsJson.getContainArticlesList().forEach(articleJson -> {
            ContainArticlesDTO containArticlesDTO = new ContainArticlesDTO();
            containArticlesDTO.setArtId(articleJson.getArtId());
            containArticlesDTO.setAmountOf(articleJson.getAmountOf());
            containArticlesDTO.setProduct(productsDTO);
            containArticlesService.save(containArticlesDTO);
        });

      });
    } catch (IOException e){
      System.out.println("Unable to save products: " + e.getMessage());
    }
  }

  @Test
  public void fetchAllProducts_ShouldReturnTotalNumber() {
    List<Products> allProducts = productsService.getAllProducts();
    Assertions.assertEquals(8, allProducts.size());
  }


}
