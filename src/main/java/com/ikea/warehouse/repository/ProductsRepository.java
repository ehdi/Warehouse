package com.ikea.warehouse.repository;

import com.ikea.warehouse.domain.Products;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductsRepository extends JpaRepository<Products, Long> {
  Optional<Products> findByName(String name);
}
