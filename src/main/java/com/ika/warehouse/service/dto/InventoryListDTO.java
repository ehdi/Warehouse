package com.ika.warehouse.service.dto;

import java.util.List;

public class InventoryListDTO {

  private List<InventoryDTO> inventory;

  public List<InventoryDTO> getInventory() {
    return inventory;
  }

  public InventoryListDTO setInventory(
      List<InventoryDTO> inventory) {
    this.inventory = inventory;
    return this;
  }

}
