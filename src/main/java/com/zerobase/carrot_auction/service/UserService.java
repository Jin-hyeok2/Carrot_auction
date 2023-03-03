package com.zerobase.carrot_auction.service;

import com.zerobase.carrot_auction.Exception.AuthException;
import com.zerobase.carrot_auction.dto.request.UserReq;
import com.zerobase.carrot_auction.dto.response.ProductDto;
import com.zerobase.carrot_auction.repository.DealRepository;
import com.zerobase.carrot_auction.repository.ProductRepository;
import com.zerobase.carrot_auction.repository.UserRepository;
import com.zerobase.carrot_auction.repository.entity.UserEntity;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final DealRepository dealRepository;
	private final ProductRepository productRepository;


	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		return this.userRepository.findByEmail(email)
			.orElseThrow(() -> new RuntimeException("email = " + email + " not found"));
	}

	public UserEntity signUp(UserReq.SignUp request) {
		boolean isExistEmail = this.userRepository.existsByEmail(request.getEmail());
		boolean isExistNickname = this.userRepository.existsByNickname(request.getNickname());
		boolean isExistPhone = this.userRepository.existsByNickname(request.getNickname());

		if (isExistEmail) {
			throw new AuthException(AuthException.ErrorCode.ALREADY_USING_EMAIL);
		}
		if (isExistNickname) {
			throw new AuthException(AuthException.ErrorCode.ALREADY_USING_NICKNAME);
		}
		if (isExistPhone) {
			throw new AuthException(AuthException.ErrorCode.ALREADY_USING_PHONE);
		}
		request.setPassword(this.passwordEncoder.encode(request.getPassword()));
		UserEntity user = request.dtoToEntity();
		String authCode = createCode();
//        log.info("authCode is " + authCode);
		user.setAuthCode(authCode);
		userRepository.save(user);

		return user;
	}

	public void verifyMail(UserReq.VerifyMail request) {
		UserEntity user = userRepository.findById(request.getId())
			.orElseThrow(() -> new AuthException(AuthException.ErrorCode.NOT_FOUND_USER));
		if (request.getAuthCode().equals(user.getAuthCode())) {
			user.setAuth(true);
			userRepository.save(user);
		} else {
			throw new AuthException(AuthException.ErrorCode.NOT_CORRECT_AUTH_CODE);
		}
	}

	public UserEntity signIn(UserReq.SignIn request) {
		UserEntity user = userRepository.findByEmail(request.getEmail())
			.orElseThrow(() -> new AuthException(AuthException.ErrorCode.NOT_FOUND_USER));
		if (!user.isAuth()) {
			throw new AuthException(AuthException.ErrorCode.UNAUTHORIZED_EMAIL);
		}
		if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
			throw new AuthException(AuthException.ErrorCode.NOT_CORRECT_PASSWORD);
		}

		return user;
	}

	public UserEntity getInfo(String email) {
		UserEntity user = userRepository.findByEmail(email)
			.orElseThrow(() -> new AuthException(AuthException.ErrorCode.NOT_FOUND_USER));

		return user;
	}

	private String createCode() {
		Random random = new Random();
		StringBuffer key = new StringBuffer();

		for (int i = 0; i < 16; i++) {
			int index = random.nextInt(3);
			switch (index) {
				case 0:
					key.append((char) (random.nextInt(26) + 97));
					break;
				case 1:
					key.append((char) (random.nextInt(26) + 65));
					break;
				case 2:
					key.append((random.nextInt(10)));
					break;
			}
		}

		return key.toString();
	}

	public UserEntity editInfo(String userEmail, UserReq.EditInfo request) {
		UserEntity user = userRepository.findByEmail(userEmail)
			.orElseThrow(() -> new AuthException(AuthException.ErrorCode.NOT_FOUND_USER));
		log.info(request.toString());
		if (!passwordEncoder.matches(request.getCurPassword(), user.getPassword())) {
			throw new AuthException(AuthException.ErrorCode.NOT_CORRECT_PASSWORD);
		}

		if (request.getPassword() != null) {
//            log.info("비밀번호 변경 요청");
			if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
				throw new AuthException(AuthException.ErrorCode.NOT_NEW_PASSWORD);
			}
			user.setPassword(passwordEncoder.encode(request.getPassword()));
		} else if (request.getNickname() != null) {
//            log.info("닉네임 변경 요청");
			if (userRepository.existsByNickname(request.getNickname())) {
				throw new AuthException(AuthException.ErrorCode.ALREADY_USING_NICKNAME);
			}
			user.setNickname(request.getNickname());
		} else if (request.getPhone() != null) {
//            log.info("연락처 변경 요청");
			if (userRepository.existsByPhone(request.getPhone())) {
				throw new AuthException(AuthException.ErrorCode.ALREADY_USING_PHONE);
			}
			user.setPhone(request.getPhone());
		} else {
			throw new RuntimeException("변경 요청 자료가 없습니다");
		}
		user = userRepository.save(user);
		return user;
	}

	public Page<ProductDto> getSalesListByEmail(String userEmail, int page, int size) {
		PageRequest pageRequest = PageRequest.of(page, size);
		UserEntity user = userRepository.findByEmail(userEmail)
			.orElseThrow(() -> new RuntimeException(""));

		return productRepository.findAllBySeller(pageRequest, user)
			.map(ProductDto::of);
	}

	public Page<ProductDto> getPurchaseListByEmail(String userEmail, int page, int size) {
		PageRequest pageRequest = PageRequest.of(page, size);
		UserEntity user = userRepository.findByEmail(userEmail)
			.orElseThrow(() -> new RuntimeException(""));

		return productRepository.findAllBySeller(pageRequest, user)
			.map((ProductDto::of));
	}
}
