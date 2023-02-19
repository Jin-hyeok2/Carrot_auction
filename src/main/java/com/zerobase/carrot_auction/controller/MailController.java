package com.zerobase.carrot_auction.controller;

import com.zerobase.carrot_auction.dto.Mail;
import com.zerobase.carrot_auction.dto.Response;
import com.zerobase.carrot_auction.service.MailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/mail")
public class MailController {
    private final MailServiceImpl mailService;

    @PutMapping
    public ResponseEntity<?> sendMail(@RequestBody Mail.Request.SendMail request) throws Exception {
        return ResponseEntity.ok(new Response("success", null));
    }
}
