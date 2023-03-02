package com.zerobase.carrot_auction.repository;


import com.zerobase.carrot_auction.repository.entity.DealEntity;
import com.zerobase.carrot_auction.repository.entity.ProductEntity;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DealRepository extends JpaRepository<DealEntity, Long> {

	@Transactional
	void deleteAllByProduct(ProductEntity product);
}