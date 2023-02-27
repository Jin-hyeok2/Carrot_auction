package com.zerobase.carrot_auction.repository;

import com.zerobase.carrot_auction.repository.entity.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

	Optional<UserEntity> findByEmail(String email);

	boolean existsByEmail(String email);

	boolean existsByNickname(String nickname);
	boolean existsByPhone(String phone);
}
