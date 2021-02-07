package com.ika.warehouse.repository;

import com.ika.warehouse.domain.Products;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductsRepository extends JpaRepository<Products, Long> {
  Optional<Products> findByName(String name);
  Optional<List<Products>> findAllByName(String string);
  void deleteAllByName(String name);
}
