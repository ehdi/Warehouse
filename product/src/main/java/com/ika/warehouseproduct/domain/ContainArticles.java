package com.ika.warehouseproduct.domain;

public class ContainArticles {

  private Long artId;
  private Long amountOf;

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

  @Override
  public String toString() {
    return "ContainArticles{" +
        ", artId=" + artId +
        ", amountOf=" + amountOf +
        '}';
  }
}
