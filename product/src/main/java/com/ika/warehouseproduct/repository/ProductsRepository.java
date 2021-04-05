package com.ika.warehouseproduct.repository;


import com.ika.warehouseproduct.domain.Products;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductsRepository extends MongoRepository<Products, String> {
  Optional<Products> findByName(String name);
  Optional<Products> findByRawName(String name);
}
