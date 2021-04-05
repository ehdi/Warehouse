package com.ika.warehouseinventory.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ContainArticlesDTO {

  @JsonProperty("art_id")
  private Long artId;
  @JsonProperty("amount_of")
  private Long amountOf;

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

  @Override
  public String toString() {
    return "ContainArticlesDTO{" +
        "artId=" + artId +
        ", amountOf=" + amountOf +
        '}';
  }
}
