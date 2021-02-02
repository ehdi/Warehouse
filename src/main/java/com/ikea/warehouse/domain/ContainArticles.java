package com.ikea.warehouse.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ContainArticles {

  @Id
  @GeneratedValue(strategy= GenerationType.AUTO)
  private Long id;
  private Long artId;
  private Long amountOf;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "id")
  private Products products;

  public Long getId() {
    return id;
  }

  public ContainArticles setId(Long id) {
    this.id = id;
    return this;
  }

  public Long getArtId() {
    return artId;
  }

  public ContainArticles setArtId(Long artId) {
    this.artId = artId;
    return this;
  }

  public Long getAmountOf() {
    return amountOf;
  }

  public ContainArticles setAmountOf(Long amountOf) {
    this.amountOf = amountOf;
    return this;
  }

  public Products getProducts() {
    return products;
  }

  public ContainArticles setProducts(Products products) {
    this.products = products;
    return this;
  }
}
