package com.ika.warehousepricing.service.dto;

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
