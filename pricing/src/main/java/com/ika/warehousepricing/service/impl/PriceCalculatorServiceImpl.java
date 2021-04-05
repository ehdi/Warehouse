package com.ika.warehousepricing.service.impl;

import com.ika.warehousepricing.enumeration.ProductType;
import com.ika.warehousepricing.service.PriceCalculatorService;
import com.ika.warehousepricing.service.dto.PriceDTO;
import java.math.BigDecimal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PriceCalculatorServiceImpl implements PriceCalculatorService {

  private final Logger log = LoggerFactory.getLogger(PriceCalculatorServiceImpl.class);

  @Override
  public PriceDTO calculate(ProductType type) {
    log.debug("Request to calculate price of {}", type);
    PriceDTO priceDTO = new PriceDTO();

    switch (type) {
      case DINING_CHAIR:
        priceDTO.setPrice(new BigDecimal(1200));
        break;
      case DINNING_TABLE:
        priceDTO.setPrice(new BigDecimal(1800));
        break;
      default:
        priceDTO.setPrice(new BigDecimal(0));
        break;
    }
    return priceDTO;
  }
}
