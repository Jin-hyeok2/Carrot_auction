package com.zerobase.carrot_auction.repository.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "deal")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Builder
public class DealEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 거래 id

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_Id", nullable = false)
    private ProductEntity product; // 경매 글 id

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_Id", nullable = false)
    private UserEntity customer; // 구매자 id

    private int price; // 가격

    @CreatedDate
    private LocalDateTime createAt; // 거래 신청시간

<<<<<<<HEAD

    public DealEntity(ProductEntity product, UserEntity customer, int price) {
        this.product = product;
        this.customer = customer;
        this.price = price;
    }
=======

    public DealEntity(ProductEntity product, UserEntity customer, int price, LocalDateTime createAt) {
        this.product = product;
        this.customer = customer;
        this.price = price;
        this.createAt = createAt;
    }
>>>>>>>main

}