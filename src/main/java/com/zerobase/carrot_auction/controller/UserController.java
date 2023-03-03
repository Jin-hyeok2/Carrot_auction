package com.zerobase.carrot_auction.controller;

import com.zerobase.carrot_auction.dto.request.UserReq;
import com.zerobase.carrot_auction.dto.response.ProductDto;
import com.zerobase.carrot_auction.dto.response.Response;
import com.zerobase.carrot_auction.dto.response.UserRes;
import com.zerobase.carrot_auction.repository.entity.UserEntity;
import com.zerobase.carrot_auction.security.TokenProvider;
import com.zerobase.carrot_auction.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

	private final UserService userService;
	private final TokenProvider tokenProvider;

	@PostMapping("/signup")
	public ResponseEntity<Response> signUp(@RequestBody UserReq.SignUp request) {
		UserEntity user = userService.signUp(request);
//		log.info(user.toString());
		UserRes.Signup data = new UserRes.Signup(user);

		return ResponseEntity.ok(new Response("success", data));
	}

	@PutMapping("/verify")
	public ResponseEntity<Response> verify(@RequestBody UserReq.VerifyMail request) {
		userService.verifyMail(request);

		return ResponseEntity.ok(new Response("success", null));
	}

	@PostMapping("/signin")
	public ResponseEntity<Response> signIn(@RequestBody UserReq.SignIn request) {
		UserRes.TokenResponse data = new UserRes.TokenResponse();
		UserEntity user = userService.signIn(request);
		data.setToken(tokenProvider.generateToken(user.getEmail(), user.getRoles()));
		return ResponseEntity.ok(new Response("success", data));
	}

	@GetMapping("/getInfo")
	public ResponseEntity<Response> getInfo(@RequestHeader(name = "Authorization") String token) {
		String userEmail = tokenProvider.getEmail(token.substring("Bearer ".length()));
		UserRes.GetInfo data = new UserRes.GetInfo(userService.getInfo(userEmail));

		return ResponseEntity.ok(new Response("success", data));
	}

	@PutMapping("/editInfo")
	public ResponseEntity<Response> editInfo(@RequestHeader(name = "Authorization") String token,
		@RequestBody UserReq.EditInfo request) {
		String userEmail = tokenProvider.getEmail(token.substring("Bearer ".length()));
		UserRes.GetInfo data = new UserRes.GetInfo(userService.editInfo(userEmail, request));
		return ResponseEntity.ok(new Response("success", data));
	}

	public ResponseEntity<Response> signOut() {
		return null;
	}

	@GetMapping("/purchase")
	public ResponseEntity<Response> purchaseHistory(
		@RequestHeader(name = "Authorization") String token,
		@RequestParam int pageNum, @RequestParam int size) {
		String userEmail = tokenProvider.getEmail(token.substring("Bearer".length()));
		Page<ProductDto> page = userService.getPurchaseListByEmail(userEmail, pageNum, size);

		return ResponseEntity.ok(new Response("", page));
	}

	@GetMapping("/sales")
	public ResponseEntity<Response> salesHistory(
		@RequestHeader(name = "Authorization") String token,
		@RequestParam int pageNum, @RequestParam int size) {
		String userEmail = tokenProvider.getEmail(token.substring("Bearer".length()));
		Page<ProductDto> page = userService.getSalesListByEmail(userEmail, pageNum, size);

		return ResponseEntity.ok(new Response("success", page));
	}

}
