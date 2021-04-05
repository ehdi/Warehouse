package com.ika.warehouseproduct.service.dto;

import com.ika.warehouseproduct.enumeration.ProductType;

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
