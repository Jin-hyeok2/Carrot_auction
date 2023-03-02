package com.zerobase.carrot_auction.controller;

import com.zerobase.carrot_auction.dto.Response;
import com.zerobase.carrot_auction.dto.User;
import com.zerobase.carrot_auction.dto.reponse.ProductDto;
import com.zerobase.carrot_auction.repository.entity.UserEntity;
import com.zerobase.carrot_auction.security.TokenProvider;
import com.zerobase.carrot_auction.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final TokenProvider tokenProvider;

    @PostMapping("/signup")
    public ResponseEntity<Response> signUp(@RequestBody User.Request.SignUp request) {
        UserEntity user = userService.signUp(request);
//		log.info(user.toString());
        User.Response.Signup data = new User.Response.Signup(user);

        return ResponseEntity.ok(new Response("success", data));
    }

    @PutMapping("/verify")
    public ResponseEntity<Response> verify(@RequestBody User.Request.VerifyMail request) {
        userService.verifyMail(request);

		return ResponseEntity.ok(new Response("success", null));
	}

    @PostMapping("/signin")
    public ResponseEntity<Response> signIn(@RequestBody User.Request.SignIn request) {
        User.Response.TokenResponse data = new User.Response.TokenResponse();
        UserEntity user = userService.signIn(request);
//		log.info(user.toString());
        data.setToken(tokenProvider.generateToken(user.getEmail(), user.getRoles()));
        return ResponseEntity.ok(new Response("success", data));
    }

    @GetMapping("/getInfo")
    public ResponseEntity<Response> getInfo(@RequestHeader(name = "Authorization") String token) {
        String userEmail = tokenProvider.getEmail(token.substring("Bearer ".length()));
        User.Response.GetInfo data = new User.Response.GetInfo(userService.getInfo(userEmail));

        return ResponseEntity.ok(new Response("success", data));
    }

    @PutMapping("/editInfo")
    public ResponseEntity<Response> editInfo(@RequestHeader(name = "Authorization") String token,
                                             @RequestBody User.Request.EditInfo request) {
        String userEmail = tokenProvider.getEmail(token.substring("Bearer ".length()));
        User.Response.GetInfo data = new User.Response.GetInfo(userService.editInfo(userEmail, request));
        return ResponseEntity.ok(new Response("success", data));
    }

    public ResponseEntity<Response> signOut() {
        return null;
    }

    @GetMapping("/purchase")
    public ResponseEntity<Response> purchaseHistory(@RequestHeader(name = "Authorization") String token,
                                                    @RequestBody PageRequest pageRequest) {
        String userEmail = tokenProvider.getEmail(token.substring("Bearer".length()));
        Page<ProductDto> page = userService.getPurchaseListByEmail(userEmail, pageRequest.getPageNumber(), pageRequest.getPageSize());

        return ResponseEntity.ok(new Response("", page));
    }

    @GetMapping("/sales")
    public ResponseEntity<Response> salesHistory(@RequestHeader(name = "Authorization") String token,
                                                 @RequestBody PageRequest pageRequest) {
        String userEmail = tokenProvider.getEmail(token.substring("Bearer".length()));
        Page<ProductDto> page = userService.getSalesListByEmail(userEmail, pageRequest.getPageNumber(), pageRequest.getPageSize());

        return ResponseEntity.ok(new Response("", page));
    }

}
