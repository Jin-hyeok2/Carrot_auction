package com.zerobase.carrot_auction.repository;

import com.zerobase.carrot_auction.repository.entity.RoleEntity;
import com.zerobase.carrot_auction.repository.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    List<RoleEntity> findByUser(UserEntity user);
}
