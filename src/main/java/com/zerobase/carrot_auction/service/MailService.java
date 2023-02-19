package com.zerobase.carrot_auction.service;

import com.zerobase.carrot_auction.dto.Mail;

public interface MailService {
    void sendMessage(Long userId) throws Exception;
}
