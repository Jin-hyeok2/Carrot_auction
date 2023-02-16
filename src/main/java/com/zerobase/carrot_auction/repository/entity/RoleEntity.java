package com.zerobase.carrot_auction.repository.entity;

import lombok.*;

import javax.management.relation.Role;
import javax.persistence.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ROLES")
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roleName;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_ID")
    private UserEntity user;

    public RoleEntity (String roleName, UserEntity user) {
        this.roleName = roleName;
        this.user = user;
    }
}
