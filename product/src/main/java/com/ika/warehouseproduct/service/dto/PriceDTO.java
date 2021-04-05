package com.ika.warehouseproduct.service.dto;

import java.math.BigDecimal;

public class PriceDTO {

  private BigDecimal price;

  public BigDecimal getPrice() {
    return price;
  }

  public PriceDTO setPrice(BigDecimal price) {
    this.price = price;
    return this;
  }
}
