package com.ika.warehousepricing.service.dto;

import com.ika.warehousepricing.enumeration.ProductType;

public class PriceCalculationDTO {

  private ProductType type;

  public PriceCalculationDTO() {
  }

  public PriceCalculationDTO(ProductType type) {
    this.type = type;
  }

  public ProductType getType() {
    return type;
  }

  public PriceCalculationDTO setType(ProductType type) {
    this.type = type;
    return this;
  }
}
