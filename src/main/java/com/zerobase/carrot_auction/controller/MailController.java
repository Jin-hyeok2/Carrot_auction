package com.zerobase.carrot_auction.controller;

import com.zerobase.carrot_auction.dto.Mail;
import com.zerobase.carrot_auction.dto.Response;
import com.zerobase.carrot_auction.service.ImpI.MailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/mail")
public class MailController {
    private final MailServiceImpl mailService;

    @GetMapping
    public ResponseEntity<?> sendMail(@RequestBody Mail.Request.SendMail request) throws Exception {
        mailService.sendMessage(request.getId());
        return ResponseEntity.ok(new Response("success", null));
    }
}
