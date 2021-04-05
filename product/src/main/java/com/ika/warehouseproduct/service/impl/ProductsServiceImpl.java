package com.ika.warehouseproduct.service.impl;

import static java.util.stream.Collectors.toList;

import com.ika.warehouseproduct.domain.Products;
import com.ika.warehouseproduct.enumeration.ProductType;
import com.ika.warehouseproduct.exception.ItemNotFoundException;
import com.ika.warehouseproduct.exception.RabbitException;
import com.ika.warehouseproduct.repository.ProductsRepository;
import com.ika.warehouseproduct.service.InventoryService;
import com.ika.warehouseproduct.service.PricingService;
import com.ika.warehouseproduct.service.ProductsService;
import com.ika.warehouseproduct.service.dto.ContainArticlesDTO;
import com.ika.warehouseproduct.service.dto.InventoryDTO;
import com.ika.warehouseproduct.service.dto.PriceCalculationDTO;
import com.ika.warehouseproduct.service.dto.PriceDTO;
import com.ika.warehouseproduct.service.dto.ProductsDTO;
import com.ika.warehouseproduct.service.mapper.ProductsMapper;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductsServiceImpl implements ProductsService {

  private final Logger log = LoggerFactory.getLogger(ProductsServiceImpl.class);

  private final ProductsRepository productsRepository;
  private final ProductsMapper productsMapper;
  private final RabbitTemplate rabbitTemplate;
  private final InventoryService inventoryService;
  private final PricingService pricingService;

  public ProductsServiceImpl(ProductsRepository productsRepository,
      ProductsMapper productsMapper,
      RabbitTemplate rabbitTemplate,
      InventoryService inventoryService,
      PricingService pricingService) {
    this.productsRepository = productsRepository;
    this.productsMapper = productsMapper;
    this.rabbitTemplate = rabbitTemplate;
    this.inventoryService = inventoryService;
    this.pricingService = pricingService;
  }

  @Override
  public List<ProductsDTO> getAllProducts(Pageable pageable) {
    log.debug("Request to fetch all products");
    return productsMapper.toDTO(productsRepository.findAll(pageable).toList());
  }

  @Override
  public ProductsDTO save(ProductsDTO productsDTO) {
    log.debug("Request to save an item in products : {}", productsDTO);
    Products savedProduct = productsRepository.save(
        productsMapper.toEntity(productsDTO)
    );
    return productsMapper.toDTO(savedProduct);
  }

  @Override
  public Optional<ProductsDTO> findByName(String name) {
    log.debug("Request to find by name : {}", name);
    return productsRepository.findByName(name)
        .map(productsMapper::toDTO);
  }

  @Override
  public List<ProductsDTO> getProductByInventoryQuantity() {
    log.debug("Request to get product by inventory quantity");

    List<InventoryDTO> listOfItemsInInventory = inventoryService.getListOfItemsInInventory(0, 10);

    Pageable paging = PageRequest.of(0, 2);
    List<ProductsDTO> allProducts = this.getAllProducts(paging);
    System.out.println();
    return allProducts.stream()
        .filter(products -> {

          PriceDTO calculatedProduct = pricingService.calculate(
              new PriceCalculationDTO(ProductType.valueOf(
                  StringUtils
                      .upperCase(products.getName().replaceAll(" ", "_"))
              )));
          products.setPrice(calculatedProduct.getPrice());

          AtomicReference<Boolean> productStatus = new AtomicReference<>(true);
          List<ContainArticlesDTO> containArticlesList = products.getContainArticlesList();
          containArticlesList.forEach(item -> {
            Optional<InventoryDTO> matchingInventory = listOfItemsInInventory.stream().
                filter(inventoryItem -> inventoryItem.getArtId().equals(item.getArtId())).
                findFirst();

            if (matchingInventory.isPresent()) {
              if (matchingInventory.get().getStock() < 1)
                productStatus.set(false);
            }

          });

          return productStatus.get();
        })
        .collect(toList());
  }

  @Override
  public void remove(String name) {
    log.debug("Request to remove a product : {}", name);

    Products products = productsRepository.findByRawName(name)
        .map(productItem -> {

          try{
            log.info("Send message to inventory queue - {}", productItem);
            rabbitTemplate.convertAndSend("inventoryQueue",
                "messages.key", productsMapper.toDTO(productItem));
            return productItem;
          }catch (AmqpException e){
            throw new RabbitException();
          }

        }).orElseThrow(ItemNotFoundException::new);

    productsRepository.delete(products);
  }


  public static ProductType valueOfLabel(String label) {
    for (ProductType e : ProductType.values()) {
      if (e.label.equals(label)) {
        return e;
      }
    }
    return null;
  }
}
