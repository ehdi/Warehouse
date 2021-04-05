package com.ika.warehouseinventory.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.List;

public class ProductsDTO implements Serializable {

  private String name;
  private String rawName;
  @JsonProperty("contain_articles")
  private List<ContainArticlesDTO> containArticlesList;

  public ProductsDTO() {
  }

  public ProductsDTO(String name,
      List<ContainArticlesDTO> containArticlesList) {
    this.name = name;
    this.containArticlesList = containArticlesList;
  }

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
