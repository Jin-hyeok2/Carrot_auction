package com.zerobase.carrot_auction.service;

import com.zerobase.carrot_auction.dto.User;
import com.zerobase.carrot_auction.dto.User.Response.Signup;
import com.zerobase.carrot_auction.repository.RoleRepository;
import com.zerobase.carrot_auction.repository.UserRepository;
import com.zerobase.carrot_auction.repository.entity.RoleEntity;
import com.zerobase.carrot_auction.repository.entity.UserEntity;
import java.util.stream.Collectors;

import com.zerobase.carrot_auction.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		return this.userRepository.findByEmail(email)
			.orElseThrow(() -> new RuntimeException("Email: " + email + " not found"));
	}

	public UserEntity signUp(User.Request.SignUp request) {
		boolean isExistEmail = this.userRepository.existsByEmail(request.getEmail());
		boolean isExistNickname = this.userRepository.existsByNickname(request.getNickname());
		boolean isExistPhone = this.userRepository.existsByNickname(request.getNickname());

        if (isExistEmail) {
            throw new RuntimeException("이미 사용 중인 아이디입니다.");
        }
        if (isExistNickname) {
            throw new RuntimeException("이미 사용 중인 닉네임입니다.");
        }
        if (isExistPhone) {
            throw new RuntimeException("이미 사용 중인 핸드폰 번호입니다.");
        }
        request.setPassword(this.passwordEncoder.encode(request.getPassword()));
        UserEntity user = request.dtoToEntity();
        String authCode = createCode();
//        log.info("authCode is " + authCode);
        user.setAuthCode(authCode);
        userRepository.save(user);

        return user;
    }

    public void verifyMail(User.Request.VerifyMail request) {
        UserEntity user = userRepository.findById(request.getId())
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다"));
        if(request.getAuthCode().equals(user.getAuthCode())) {
            user.setAuth(true);
            userRepository.save(user);
        } else {
            throw new RuntimeException("인증코드가 맞지 않습니다");
        }
    }

    public UserEntity signIn(User.Request.SignIn request) {
        UserEntity user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("회원을 찾을 수 없습니다"));
        if(!user.isAuth()) {
            throw new RuntimeException("이메일 인증이 완료되지 않았습니다");
        }
        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다");
        }

        return user;
    }

    private String createCode() {
        Random random = new Random();
        StringBuffer key = new StringBuffer();

        for (int i = 0; i < 16; i++) {
            int index = random.nextInt(3);
            switch (index) {
                case 0:
                    key.append((char)(random.nextInt(26) + 97));
                    break;
                case 1:
                    key.append((char)(random.nextInt(26) + 65));
                    break;
                case 2:
                    key.append((random.nextInt(10)));
                    break;
            }
        }

        return key.toString();
    }
}
