package com.ika.warehouseproduct.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.List;

public class ProductsDTO {

  private String name;
  private String rawName;
  private BigDecimal price;
  @JsonProperty("contain_articles")
  private List<ContainArticlesDTO> containArticlesList;


  public String getName() {
    return name;
  }

  public ProductsDTO setName(String name) {
    this.name = name;
    return this;
  }

  public String getRawName() {
    return rawName;
  }

  public ProductsDTO setRawName(String rawName) {
    this.rawName = rawName;
    return this;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public ProductsDTO setPrice(BigDecimal price) {
    this.price = price;
    return this;
  }

  public List<ContainArticlesDTO> getContainArticlesList() {
    return containArticlesList;
  }

  public ProductsDTO setContainArticlesList(
      List<ContainArticlesDTO> containArticlesList) {
    this.containArticlesList = containArticlesList;
    return this;
  }

  @Override
  public String toString() {
    return "ProductsDTO{" +
        ", name='" + name + '\'' +
        ", containArticlesList=" + containArticlesList +
        '}';
  }
}
