package com.zerobase.carrot_auction.dto.response;

import com.zerobase.carrot_auction.repository.entity.RoleEntity;
import com.zerobase.carrot_auction.repository.entity.UserEntity;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;

public class UserRes {

	@Data
	public static class Signup {

		private Long id;
		private String email;
		private String nickname;
		private String phone;
		private List<String> roles;

		public Signup(UserEntity userEntity) {
			this.id = userEntity.getId();
			this.email = userEntity.getEmail();
			this.nickname = userEntity.getNickname();
			this.phone = userEntity.getPhone();
			this.roles = userEntity.getRoles()
				.stream()
				.map(RoleEntity::getRoleName)
				.collect(Collectors.toList());
		}
	}

	@Data
	public static class TokenResponse {

		private String Token;
	}

	@Data
	public static class GetInfo {

		private String email;
		private String nickname;
		private String phone;
		private float temperature;

		public GetInfo(UserEntity userEntity) {
			this.email = userEntity.getEmail();
			this.nickname = userEntity.getNickname();
			this.phone = userEntity.getPhone();
			this.temperature = userEntity.getTemperature();
		}
	}
}
