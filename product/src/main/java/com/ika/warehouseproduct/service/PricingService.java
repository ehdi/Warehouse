package com.ika.warehouseproduct.service;

import com.ika.warehouseproduct.service.dto.PriceCalculationDTO;
import com.ika.warehouseproduct.service.dto.PriceDTO;

public interface PricingService {

  PriceDTO calculate(PriceCalculationDTO priceCalculationDTO);

}
