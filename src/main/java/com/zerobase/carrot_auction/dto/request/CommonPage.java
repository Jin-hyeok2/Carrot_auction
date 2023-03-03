package com.zerobase.carrot_auction.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonPage {

    private long pageNum = 1;
    private long pageSize = 10;
}
