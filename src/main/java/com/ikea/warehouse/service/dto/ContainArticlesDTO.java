package com.ikea.warehouse.service.dto;

public class ContainArticlesDTO {

  private Long id;
  private Long artId;
  private Long amountOf;

  public Long getId() {
    return id;
  }

  public ContainArticlesDTO setId(Long id) {
    this.id = id;
    return this;
  }

  public Long getArtId() {
    return artId;
  }

  public ContainArticlesDTO setArtId(Long artId) {
    this.artId = artId;
    return this;
  }

  public Long getAmountOf() {
    return amountOf;
  }

  public ContainArticlesDTO setAmountOf(Long amountOf) {
    this.amountOf = amountOf;
    return this;
  }
}
