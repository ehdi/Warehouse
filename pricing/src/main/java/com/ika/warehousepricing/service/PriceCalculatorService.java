package com.ika.warehousepricing.service;

import com.ika.warehousepricing.enumeration.ProductType;
import com.ika.warehousepricing.service.dto.PriceDTO;

public interface PriceCalculatorService {

  PriceDTO calculate(ProductType type);

}
