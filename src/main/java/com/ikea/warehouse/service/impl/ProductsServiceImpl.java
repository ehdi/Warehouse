package com.ikea.warehouse.service.impl;

import com.ikea.warehouse.domain.Products;
import com.ikea.warehouse.repository.ProductsRepository;
import com.ikea.warehouse.service.ProductsService;
import com.ikea.warehouse.service.dto.ProductsDTO;
import com.ikea.warehouse.service.mapper.ProductsMapper;
import java.util.List;
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

  public ProductsServiceImpl(EntityManagerFactory emf,
      ProductsRepository productsRepository,
      ProductsMapper productsMapper) {
    this.emf = emf;
    this.productsRepository = productsRepository;
    this.productsMapper = productsMapper;
  }

  @Transactional
  @Override
  public List<Products> getAllProducts() {
    log.debug("Request to fetch all products");

    EntityManager em = emf.createEntityManager();
    em.getTransaction().begin();

    EntityGraph<?> entityGraph = em.createEntityGraph("graph.Products.containArticlesList");
    TypedQuery<Products> q = em.createQuery("SELECT DISTINCT a FROM Products a", Products.class)
        .setHint("javax.persistence.fetchgraph", entityGraph);
    List<Products> products = q.getResultList();

    em.getTransaction().commit();
    em.close();

    return products;
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

}
