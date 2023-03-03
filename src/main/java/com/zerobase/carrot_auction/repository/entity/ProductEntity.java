package com.zerobase.carrot_auction.repository.entity;

import com.sun.istack.NotNull;
import com.zerobase.carrot_auction.repository.entity.code.Status;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "product")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@EntityListeners(AuditingEntityListener.class)
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = true)
    private UserEntity customer;
    @ManyToOne
    @JoinColumn(name = "seller_id", nullable = false)
    private UserEntity seller;
    @NotNull
    private boolean auctionYn;
    @NotNull
    @Enumerated(value = EnumType.STRING)
    private Status status;
    private String title;
    private String siDo;
    private String guGun;
    private int price;
    private String description;
    private int endPeriod;
    @CreatedDate
    private LocalDateTime createAt;

    public String getSellerNickName() {
        return seller.getNickname();
    }

    public String getCustomerNickName() {
        if (customer != null) {
            return customer.getNickname();
        }
        return null;
    }
}
