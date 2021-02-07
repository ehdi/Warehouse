package com.ika.warehouse.repository;

import com.ika.warehouse.domain.ContainArticles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContainArticlesRepository extends JpaRepository<ContainArticles, Long> {
}
