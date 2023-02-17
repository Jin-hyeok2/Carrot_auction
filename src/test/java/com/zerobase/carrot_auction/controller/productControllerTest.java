package com.zerobase.carrot_auction.controller;

import com.zerobase.carrot_auction.model.ProductForm;
import org.junit.jupiter.api.Test;

class productControllerTest {

	@Test
	void create() {
		ProductForm productForm = ProductForm.builder().title("제목").price(10).description("매우 저렴합니다.").siDo("서울시").guGun("관악구").endPeriod(2).build();
		Long sellerId = 1L;
	}
}