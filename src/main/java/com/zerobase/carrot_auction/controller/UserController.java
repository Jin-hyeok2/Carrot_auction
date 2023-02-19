package com.zerobase.carrot_auction.controller;

import com.zerobase.carrot_auction.dto.Response;
import com.zerobase.carrot_auction.dto.User;
import com.zerobase.carrot_auction.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

	private final UserService userService;

	@PostMapping("/signup")
	public ResponseEntity<?> signUp(@RequestBody User.Request.SignUp request) {
		User.Response.Signup user = userService.signUp(request);
		log.info(user.toString());

		return ResponseEntity.ok(new Response("success", user));
	}

    @PutMapping("/verify")
    public ResponseEntity<?> verify(@RequestBody User.Request.VerifyMail request) {
        userService.verifyMail(request);

        return ResponseEntity.ok(new Response("success", null));
    }

    public ResponseEntity<?> signIn(@RequestBody User.Request.SignIn request) {
        return null;
    }

	public ResponseEntity<?> getInfo(@RequestHeader String token) {
		return null;
	}

	public ResponseEntity<?> editInfo(@RequestHeader String token,
		@RequestBody User.Request.EditInfo request) {
		return null;
	}

	public ResponseEntity<?> signOut() {
		return null;
	}
}
