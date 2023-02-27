package com.zerobase.carrot_auction.controller;

import com.sun.security.auth.UserPrincipal;
import com.zerobase.carrot_auction.dto.DealDto;
import com.zerobase.carrot_auction.dto.Response;
import com.zerobase.carrot_auction.exception.DealException;
import com.zerobase.carrot_auction.exception.ErrorCode;
import com.zerobase.carrot_auction.model.Status;
import com.zerobase.carrot_auction.repository.DealRepository;
import com.zerobase.carrot_auction.repository.ProductRepository;
import com.zerobase.carrot_auction.repository.entity.DealEntity;
import com.zerobase.carrot_auction.repository.entity.Product;
import com.zerobase.carrot_auction.service.DealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;

@RestController
@RequestMapping("/deals")
public class DealController {

    @Autowired
    private DealService dealService;

    @PostMapping
    public ResponseEntity<Response> createDeal(@RequestBody DealDto dealDto) {
        DealDto createdDeal = dealService.createDeal(dealDto);
        return ResponseEntity.ok(new Response("success", createdDeal));
    }
    @GetMapping("/{dealId}")
    public ResponseEntity<Response> getDeal(@PathVariable Long dealId) {
        DealDto dealDto = dealService.getDeal(dealId);;
        return ResponseEntity.ok(new Response("success", dealDto));
    }

    @PutMapping("/{dealId}")
    public ResponseEntity<Response> updateDeal(@PathVariable Long dealId, @RequestBody DealDto dealDto) {
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