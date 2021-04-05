package com.ika.warehouseproduct.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InventoryDTO {

  @JsonProperty("art_id")
  private Long artId;
  private String name;
  private Long stock;

  public InventoryDTO() {
  }

  public InventoryDTO(Long artId, String name, Long stock) {
    this.artId = artId;
    this.name = name;
    this.stock = stock;
  }

  public Long getArtId() {
    return artId;
  }

  public InventoryDTO setArtId(Long artId) {
    this.artId = artId;
    return this;
  }

  public String getName() {
    return name;
  }

  public InventoryDTO setName(String name) {
    this.name = name;
    return this;
  }

  public Long getStock() {
    return stock;
  }

  public InventoryDTO setStock(Long stock) {
    this.stock = stock;
    return this;
  }
}
