package com.zerobase.carrot_auction.dto;

import lombok.Data;

public class Mail {

	public static class Request {

		@Data
		public static class SendMail {

			private Long id;
		}
	}
}
