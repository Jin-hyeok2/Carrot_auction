package com.zerobase.carrot_auction.service;

import com.zerobase.carrot_auction.dto.request.UserReq;
import com.zerobase.carrot_auction.dto.response.ProductDto;
import com.zerobase.carrot_auction.repository.DealRepository;
import com.zerobase.carrot_auction.repository.ProductRepository;
import com.zerobase.carrot_auction.repository.UserRepository;
import com.zerobase.carrot_auction.repository.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    private final DealRepository dealRepository;
    private final ProductRepository productRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return this.userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Email: " + email + " not found"));
    }

    public UserEntity signUp(UserReq.SignUp request) {
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

    public void verifyMail(UserReq.VerifyMail request) {
        UserEntity user = userRepository.findById(request.getId())
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다"));
        if (request.getAuthCode().equals(user.getAuthCode())) {
            user.setAuth(true);
            userRepository.save(user);
        } else {
            throw new RuntimeException("인증코드가 맞지 않습니다");
        }
    }

    public UserEntity signIn(UserReq.SignIn request) {
        UserEntity user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("회원을 찾을 수 없습니다"));
        if (!user.isAuth()) {
            throw new RuntimeException("이메일 인증이 완료되지 않았습니다");
        }
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다");
        }

        return user;
    }

    public UserEntity getInfo(String email) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("해당 유저를 찾을 수 없습니다"));

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
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다"));
        log.info(request.toString());
        if (!passwordEncoder.matches(request.getCurPassword(), user.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다");
        }

        if (request.getPassword() != null) {
            log.info("비밀번호 변경 요청");
            if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                throw new RuntimeException("변경 전 비밀번호와 변경 비밀번호가 같습니다");
            }
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        } else if (request.getNickname() != null) {
            log.info("닉네임 변경 요청");
            if (userRepository.existsByNickname(request.getNickname())) {
                throw new RuntimeException("이미 가입된 닉네임이 있습니다");
            }
            user.setNickname(request.getNickname());
        } else if (request.getPhone() != null) {
            log.info("연락처 변경 요청");
            if (userRepository.existsByPhone(request.getPhone())) {
                throw new RuntimeException("이미 가입된 번호가 있습니다");
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
                .map(product -> ProductDto.of(product));
    }

    public Page<ProductDto> getPurchaseListByEmail(String userEmail, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        UserEntity user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException(""));

        return productRepository.findAllBySeller(pageRequest, user)
                .map((ProductDto::of));
    }
}
