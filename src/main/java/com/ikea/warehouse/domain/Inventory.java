package com.ikea.warehouse.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Inventory {

  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private Long artId;
  private String name;
  private Long stock;

  public Long getArtId() {
    return artId;
  }

  public Inventory setArtId(Long artId) {
    this.artId = artId;
    return this;
  }

  public String getName() {
    return name;
  }

  public Inventory setName(String name) {
    this.name = name;
    return this;
  }

  public Long getStock() {
    return stock;
  }

  public Inventory setStock(Long stock) {
    this.stock = stock;
    return this;
  }
}
