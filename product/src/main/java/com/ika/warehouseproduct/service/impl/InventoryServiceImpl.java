package com.ika.warehouseproduct.service.impl;

import com.ika.warehouseproduct.service.InventoryService;
import com.ika.warehouseproduct.service.dto.InventoryDTO;
import java.net.URI;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@Configuration
public class InventoryServiceImpl implements InventoryService {

  private final Logger log = LoggerFactory.getLogger(InventoryServiceImpl.class);

  @Value("${application.external.inventory}")
  private String inventoryBaseUrl;

  private final RestTemplate restTemplate;

  public InventoryServiceImpl(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }


  @Override
  public List<InventoryDTO> getListOfItemsInInventory(Integer page, Integer size) {
    log.debug("request to get items of inventory from inventory service - page : {} size: {}",page,size);

    URI targetUrl= UriComponentsBuilder.fromUriString(inventoryBaseUrl)
        .path("/v1/inventory/all")
        .queryParam("page", page)
        .queryParam("size", size)
        .build()
        .encode()
        .toUri();

    return restTemplate.exchange(
          targetUrl,
          HttpMethod.GET,
          null,
          new ParameterizedTypeReference<List<InventoryDTO>>(){}
        ).getBody();
  }
}
