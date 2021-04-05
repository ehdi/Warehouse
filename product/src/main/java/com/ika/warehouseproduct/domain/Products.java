package com.ika.warehouseproduct.domain;

import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "products")
public class Products {

  @Id
  private String id;
  private String name;
  private String rawName;
  private List<ContainArticles> containArticlesList;

  public Products() {
  }

  public Products(String id, String name, String rawName,
      List<ContainArticles> containArticlesList) {
    this.id = id;
    this.name = name;
    this.rawName = rawName;
    this.containArticlesList = containArticlesList;
  }

  public String getId() {
    return id;
  }

  public Products setId(String id) {
    this.id = id;
    return this;
  }

  public String getName() {
    return name;
  }

  public Products setName(String name) {
    this.name = name;
    return this;
  }

  public String getRawName() {
    return rawName;
  }

  public Products setRawName(String rawName) {
    this.rawName = rawName;
    return this;
  }

  public List<ContainArticles> getContainArticlesList() {
    return containArticlesList;
  }

  public Products setContainArticlesList(
      List<ContainArticles> containArticlesList) {
    this.containArticlesList = containArticlesList;
    return this;
  }
}
