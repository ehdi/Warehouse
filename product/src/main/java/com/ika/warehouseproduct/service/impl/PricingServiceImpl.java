package com.ika.warehouseproduct.service.impl;

import com.ika.warehouseproduct.service.PricingService;
import com.ika.warehouseproduct.service.dto.InventoryDTO;
import com.ika.warehouseproduct.service.dto.PriceCalculationDTO;
import com.ika.warehouseproduct.service.dto.PriceDTO;
import java.net.URI;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@Configuration
public class PricingServiceImpl implements PricingService {

  private final Logger log = LoggerFactory.getLogger(PricingServiceImpl.class);

  @Value("${application.external.pricing}")
  private String pricingBaseUrl;

  private final RestTemplate restTemplate;

  public PricingServiceImpl(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @Override
  public PriceDTO calculate(PriceCalculationDTO priceCalculationDTO) {
    log.debug("request to calculate price of {}", priceCalculationDTO.getType());

    URI targetUrl= UriComponentsBuilder.fromUriString(pricingBaseUrl)
        .path("/v1/pricing/calculate")
        .build()
        .encode()
        .toUri();

    HttpEntity<PriceCalculationDTO> request = new HttpEntity<>(priceCalculationDTO);

    return restTemplate.exchange(
        targetUrl,
        HttpMethod.POST,
        request,
        PriceDTO.class
    ).getBody();
  }
}
