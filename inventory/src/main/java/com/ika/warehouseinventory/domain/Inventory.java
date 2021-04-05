package com.ika.warehouseinventory.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "inventory")
public class Inventory {

  @Id
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

  @Override
  public String toString() {
    return "Inventory{" +
        "artId=" + artId +
        ", name='" + name + '\'' +
        ", stock=" + stock +
        '}';
  }
}
