package com.ika.warehousepricing.controller;

import com.ika.warehousepricing.service.PriceCalculatorService;
import com.ika.warehousepricing.service.dto.PriceCalculationDTO;
import com.ika.warehousepricing.service.dto.PriceDTO;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/pricing")
public class PriceController {

  private final PriceCalculatorService priceCalculatorService;

  public PriceController(
      PriceCalculatorService priceCalculatorService) {
    this.priceCalculatorService = priceCalculatorService;
  }

  /**
   * Calculate price of a product by product type
   *
   * @param priceCalculationDTO is DTO request
   * @return it will return price of a product as PriceDTO object
   */
  @PostMapping("/calculate")
  public ResponseEntity<PriceDTO> calculate(@Valid @RequestBody
      PriceCalculationDTO priceCalculationDTO) {
    return ResponseEntity
        .ok(priceCalculatorService.calculate(priceCalculationDTO.getType()));
  }


}
