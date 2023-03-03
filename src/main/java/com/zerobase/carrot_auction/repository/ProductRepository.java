package com.zerobase.carrot_auction.repository;

import com.zerobase.carrot_auction.repository.entity.ProductEntity;
import com.zerobase.carrot_auction.repository.entity.UserEntity;
import com.zerobase.carrot_auction.repository.entity.code.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    List<ProductEntity> findByStatusNot(Status status);

    Page<ProductEntity> findAllBySeller(Pageable page, UserEntity userEntity);
}
