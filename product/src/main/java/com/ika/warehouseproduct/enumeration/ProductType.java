package com.ika.warehouseproduct.enumeration;

public enum ProductType {
  DINING_CHAIR("diningchair"), DINNING_TABLE("dinningtable");

  public final String label;

  ProductType(String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }
}
