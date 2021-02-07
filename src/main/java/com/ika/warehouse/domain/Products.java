package com.ika.warehouse.domain;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;

@NamedEntityGraph(
    name = "graph.Products.containArticlesList",
    attributeNodes = @NamedAttributeNode("containArticlesList")
)
@Entity
public class Products {

  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  private Long id;
  private String name;

  @OneToMany(mappedBy = "product", fetch = FetchType.LAZY,
      cascade = CascadeType.ALL)
  private List<ContainArticles> containArticlesList;

  public Products() {
  }

  public Products(Long id, String name,
      List<ContainArticles> containArticlesList) {
    this.id = id;
    this.name = name;
    this.containArticlesList = containArticlesList;
  }

  public Long getId() {
    return id;
  }

  public Products setId(Long id) {
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

  public List<ContainArticles> getContainArticlesList() {
    return containArticlesList;
  }

  public Products setContainArticlesList(
      List<ContainArticles> containArticlesList) {
    this.containArticlesList = containArticlesList;
    return this;
  }
}
