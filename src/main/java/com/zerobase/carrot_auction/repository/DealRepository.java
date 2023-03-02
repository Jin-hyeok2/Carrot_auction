package com.zerobase.carrot_auction.repository;


import com.zerobase.carrot_auction.repository.entity.DealEntity;
import com.zerobase.carrot_auction.repository.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DealRepository extends JpaRepository<DealEntity, Long> {
    Page<DealEntity> findByCustomer(Pageable pageable, UserEntity customer);
}