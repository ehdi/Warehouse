package com.ika.warehouseinventory.repository;

import com.ika.warehouseinventory.domain.Inventory;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends MongoRepository<Inventory, Long> {
  Optional<Inventory> findByArtId(Long id);
}
