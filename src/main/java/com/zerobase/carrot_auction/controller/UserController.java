package com.zerobase.carrot_auction.controller;

import com.zerobase.carrot_auction.dto.Response;
import com.zerobase.carrot_auction.dto.User;
import com.zerobase.carrot_auction.repository.entity.UserEntity;
import com.zerobase.carrot_auction.security.TokenProvider;
import com.zerobase.carrot_auction.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
	public ResponseEntity<?> signUp(@RequestBody User.Request.SignUp request) {
		UserEntity user = userService.signUp(request);
//		log.info(user.toString());
		User.Response.Signup data = new User.Response.Signup(user);

		return ResponseEntity.ok(new Response("success", data));
	}

	@PutMapping("/verify")
	public ResponseEntity<?> verify(@RequestBody User.Request.VerifyMail request) {
		userService.verifyMail(request);

		return ResponseEntity.ok(new Response("success", null));
	}

	@PostMapping("/signin")
	public ResponseEntity<?> signIn(@RequestBody User.Request.SignIn request) {
		User.Response.TokenResponse data = new User.Response.TokenResponse();
		UserEntity user = userService.signIn(request);
//		log.info(user.toString());
		data.setToken(tokenProvider.generateToken(user.getEmail(), user.getRoles()));
		return ResponseEntity.ok(new Response("success", data));
	}

	@GetMapping("/getInfo")
	public ResponseEntity<?> getInfo(@RequestHeader(name = "Authorization") String token) {
		String userEmail = tokenProvider.getEmail(token.substring("Bearer ".length()));
		User.Response.GetInfo data = new User.Response.GetInfo(userService.getInfo(userEmail));

		return ResponseEntity.ok(new Response("success", data));
	}

	@PutMapping("/editInfo")
	public ResponseEntity<?> editInfo(@RequestHeader(name = "Authorization") String token,
		@RequestBody User.Request.EditInfo request) {
		String userEmail = tokenProvider.getEmail(token.substring("Bearer ".length()));
		User.Response.GetInfo data = new User.Response.GetInfo(
			userService.editInfo(userEmail, request));
		return ResponseEntity.ok(new Response("success", data));
	}

	public ResponseEntity<?> signOut() {
		return null;
	}
}
