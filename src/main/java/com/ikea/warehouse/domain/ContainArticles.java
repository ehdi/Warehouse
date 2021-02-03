package com.ikea.warehouse.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ContainArticles {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Long artId;
  private Long amountOf;

  @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
  @JoinColumn(name = "product_id", nullable = false)
  private Products product;

  public ContainArticles() {
  }

  public ContainArticles(Long id, Long artId, Long amountOf,
      Products product) {
    this.id = id;
    this.artId = artId;
    this.amountOf = amountOf;
    this.product = product;
  }

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

  public Products getProduct() {
    return product;
  }

  public ContainArticles setProduct(Products product) {
    this.product = product;
    return this;
  }
}
