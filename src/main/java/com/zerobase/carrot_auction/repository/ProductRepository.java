package com.zerobase.carrot_auction.repository;

import com.zerobase.carrot_auction.repository.entity.Product;
import com.zerobase.carrot_auction.repository.entity.code.Status;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	List<Product> findByStatusNot(Status status);
}
