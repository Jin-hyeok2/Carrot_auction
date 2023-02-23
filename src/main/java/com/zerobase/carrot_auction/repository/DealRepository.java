package com.zerobase.carrot_auction.repository;


import com.zerobase.carrot_auction.repository.entity.DealEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DealRepository extends JpaRepository<DealEntity, Long> {

}