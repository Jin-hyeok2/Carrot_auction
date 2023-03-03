package com.zerobase.carrot_auction.dto.request;

import lombok.Data;

public class MailSender {

    public static class Request {

        @Data
        public static class SendMail {

            private Long id;
        }
    }
}
