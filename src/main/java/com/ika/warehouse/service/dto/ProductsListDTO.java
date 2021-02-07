package com.ika.warehouse.service.dto;

import java.util.List;

public class ProductsListDTO {

  private List<ProductsDTO> products;

  public List<ProductsDTO> getProducts() {
    return products;
  }

  public ProductsListDTO setProducts(
      List<ProductsDTO> products) {
    this.products = products;
    return this;
  }
}
