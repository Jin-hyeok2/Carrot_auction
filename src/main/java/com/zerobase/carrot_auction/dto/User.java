package com.zerobase.carrot_auction.dto;

import com.zerobase.carrot_auction.repository.entity.RoleEntity;
import com.zerobase.carrot_auction.repository.entity.UserEntity;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

public class User {

    public static class Request {
        @Data
        @Builder
        public static class SignUp {
            private String email;
            private String password;
            private String nickname;
            private String phone;
            private List<String> roles;


            public UserEntity dtoToEntity() {
                return UserEntity.builder()
                        .email(this.email)
                        .password(this.password)
                        .nickname(this.nickname)
                        .phone(this.phone)
                        .isAuth(false)
                        .authCode("")
                        .temperature(0)
                        .build();
            }
        }

        @Getter
        @RequiredArgsConstructor
        @ToString
        public static class SignIn {
            private String email;
            private String password;
        }

        @Data
        @Builder
        public static class GetInfo {
            private String email;
            private String nickname;
            private String phone;

            public GetInfo entityToDto(UserEntity userEntity) {
                return GetInfo.builder()
                        .email(userEntity.getEmail())
                        .nickname(userEntity.getNickname())
                        .phone(userEntity.getPhone())
                        .build();
            }
        }

        @Getter
        @RequiredArgsConstructor
        @ToString
        public static class EditInfo {
            private String curPassword;
            private String password;
            private String phone;
            private String nickname;
        }

    }

    public static class Response {
        @Data
        public static class Signup {
            private String email;
            private String nickname;
            private String phone;
            private List<String> roles;

            public Signup(UserEntity userEntity, List<RoleEntity> roleEntity) {
                this.email = userEntity.getEmail();
                this.nickname = userEntity.getNickname();
                this.phone = userEntity.getPhone();
                this.roles = roleEntity
                        .stream()
                        .map(RoleEntity::getRoleName)
                        .collect(Collectors.toList());
            }
        }
    }

}
