package com.zerobase.carrot_auction.controller;

import com.zerobase.carrot_auction.dto.request.MailSender;
import com.zerobase.carrot_auction.dto.response.Response;
import com.zerobase.carrot_auction.service.ImpI.MailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/mail")
public class MailController {

	private final MailServiceImpl mailService;

	@GetMapping
	public ResponseEntity<?> sendMail(@RequestBody MailSender.Request.SendMail request) throws Exception {
		mailService.sendMessage(request.getId());
		return ResponseEntity.ok(new Response("success", null));
	}
}
