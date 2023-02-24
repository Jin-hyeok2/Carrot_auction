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
        @NoArgsConstructor
        @AllArgsConstructor
        public static class SignUp {

            private String email;
            private String password;
            private String nickname;
            private String phone;
            private List<String> roles;


            public UserEntity dtoToEntity() {
                UserEntity user = UserEntity.builder()
                        .email(this.email)
                        .password(this.password)
                        .nickname(this.nickname)
                        .phone(this.phone)
                        .isAuth(false)
                        .temperature(0)
                        .build();

                user.setRoles(this.roles
                        .stream()
                        .map(role -> new RoleEntity(role, user))
                        .collect(Collectors.toList()));
                return user;
            }
        }

        @Getter
        @RequiredArgsConstructor
        @ToString
        public static class SignIn {

            private String email;
            private String password;
        }


        @Getter
        @RequiredArgsConstructor
        public static class VerifyMail {
            private Long id;
            private String authCode;
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
}
