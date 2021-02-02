package com.ikea.warehouse.service.dto;

import java.util.List;

public class ProductsDTO {

  private Long id;
  private String name;
  private List<ContainArticlesDTO> containArticlesList;

  public Long getId() {
    return id;
  }

  public ProductsDTO setId(Long id) {
    this.id = id;
    return this;
  }

  public String getName() {
    return name;
  }

  public ProductsDTO setName(String name) {
    this.name = name;
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
}
