package com.zerobase.carrot_auction.repository;

import com.zerobase.carrot_auction.model.Status;
import com.zerobase.carrot_auction.repository.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByStatusNot(Status status);
}
