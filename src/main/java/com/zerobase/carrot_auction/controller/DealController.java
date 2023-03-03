package com.zerobase.carrot_auction.controller;

import com.zerobase.carrot_auction.dto.DealDto;
import com.zerobase.carrot_auction.dto.response.Response;
import com.zerobase.carrot_auction.service.DealService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/deals")
@RequiredArgsConstructor
public class DealController {


	private final DealService dealService;

	@PostMapping
	public ResponseEntity<Response> createDeal(@RequestBody DealDto dealDto) {
		DealDto createdDeal = dealService.createDeal(dealDto);
		return ResponseEntity.ok(new Response("success", createdDeal));
	}

	@GetMapping("/{dealId}")
	public ResponseEntity<Response> getDeal(@PathVariable Long dealId) {
		DealDto dealDto = dealService.getDeal(dealId);
		return ResponseEntity.ok(new Response("success", dealDto));
	}

	@PutMapping("/{dealId}")
	public ResponseEntity<Response> updateDeal(@PathVariable Long dealId,
		@RequestBody DealDto dealDto) {
		DealDto updatedDeal = dealService.updateDeal(dealId, dealDto);
		return ResponseEntity.ok(new Response("success", updatedDeal));
	}

	@DeleteMapping("/{dealId}")
	public ResponseEntity<Void> deleteDeal(@PathVariable Long dealId) {
		dealService.deleteDeal(dealId);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/{dealId}/complete")
	public ResponseEntity<Void> completeDeal(@PathVariable Long dealId,
		@RequestParam("sellerId") Long sellerId) {
		dealService.completeDeal(dealId, sellerId);
		return ResponseEntity.ok().build();
	}
}