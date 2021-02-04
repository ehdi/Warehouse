package com.ikea.warehouse.service.impl;

import static java.util.stream.Collectors.groupingBy;

import com.ikea.warehouse.domain.Products;
import com.ikea.warehouse.exception.ItemNotFoundException;
import com.ikea.warehouse.repository.ProductsRepository;
import com.ikea.warehouse.service.InventoryService;
import com.ikea.warehouse.service.ProductsService;
import com.ikea.warehouse.service.dto.ContainArticlesDTO;
import com.ikea.warehouse.service.dto.InventoryDTO;
import com.ikea.warehouse.service.dto.ProductsDTO;
import com.ikea.warehouse.service.mapper.ProductsMapper;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ProductsServiceImpl implements ProductsService {

  private final Logger log = LoggerFactory.getLogger(ProductsServiceImpl.class);

  private final EntityManagerFactory emf;

  private final ProductsRepository productsRepository;
  private final ProductsMapper productsMapper;
  private final InventoryService inventoryService;

  public ProductsServiceImpl(EntityManagerFactory emf,
      ProductsRepository productsRepository,
      ProductsMapper productsMapper, InventoryService inventoryService) {
    this.emf = emf;
    this.productsRepository = productsRepository;
    this.productsMapper = productsMapper;
    this.inventoryService = inventoryService;
  }

  @Transactional
  @Override
  public List<ProductsDTO> getAllProducts() {
    log.debug("Request to fetch all products");

    EntityManager em = emf.createEntityManager();
    em.getTransaction().begin();

    EntityGraph<?> entityGraph = em.createEntityGraph("graph.Products.containArticlesList");
    TypedQuery<Products> q = em.createQuery("SELECT a FROM Products a", Products.class)
        .setHint("javax.persistence.fetchgraph", entityGraph);
    List<Products> products = q.getResultList();

    em.getTransaction().commit();
    em.close();

    return productsMapper.toDTO(products);
  }

  @Transactional
  @Override
  public ProductsDTO save(ProductsDTO productsDTO) {
    log.debug("Request to save an item in products : {}", productsDTO);
    Products savedProduct = productsRepository.save(
        productsMapper.toEntity(productsDTO)
    );
    return productsMapper.toDTO(savedProduct);
  }

  @Transactional
  @Override
  public Optional<ProductsDTO> findByName(String name) {
    log.debug("Request to find by name : {}", name);
    return productsRepository.findByName(name)
    .map(productsMapper::toDTO);
  }

  @Override
  public Map<String, List<ProductsDTO>> getProductByInventoryQuantity() {
    log.debug("Request to get product by inventory quantity");
    List<ProductsDTO> allProducts = this.getAllProducts();

    return allProducts.stream()
        .filter(products -> {

          AtomicReference<Boolean> productStatus = new AtomicReference<>(true);
          List<ContainArticlesDTO> containArticlesList = products.getContainArticlesList();
          containArticlesList.forEach(item -> {

            InventoryDTO inventoryDTO = inventoryService.findById(item.getArtId())
                .orElseThrow(ItemNotFoundException::new);

            if (inventoryDTO.getStock() < 1)
              productStatus.set(false);
          });

          return productStatus.get();
        })
        .collect(groupingBy(ProductsDTO::getName));
  }

  @Transactional
  @Override
  public void remove(String name) {
    log.debug("Request to remove a product : {}", name);
    productsRepository.findAllByName(name)
        .map( products -> {
          if(products.size() == 0) {
            throw new ItemNotFoundException();
          }else {

            products.forEach(productItem -> {
              productItem.getContainArticlesList().forEach(containArticles ->{
                InventoryDTO inventoryDTO = new InventoryDTO();
                inventoryDTO.setArtId(containArticles.getArtId());
                inventoryDTO.setStock(containArticles.getAmountOf());
                inventoryService.update(inventoryDTO);
              });
            });

            return products;
          }
        });
    productsRepository.deleteAllByName(name);
  }

}
