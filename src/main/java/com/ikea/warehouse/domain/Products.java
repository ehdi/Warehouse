package com.ikea.warehouse.domain;

import static javax.persistence.CascadeType.ALL;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Products {

  @Id
  @GeneratedValue(strategy= GenerationType.AUTO)
  private Long id;
  private String name;

  @OneToMany(mappedBy = "product", cascade = ALL)
  private List<ContainArticles> containArticlesList;

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
