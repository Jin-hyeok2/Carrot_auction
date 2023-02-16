package com.zerobase.carrot_auction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Response {
    private String message;
    private Object data;
}
