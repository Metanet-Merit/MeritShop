package com.merit.meritShop.order.domain;

import com.merit.meritShop.user.domain.User;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@DynamicInsert
@NoArgsConstructor
@Table(name="pay")
public class Pay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="pay_id")
    private Long payId;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="order_id")
    private Orders orders;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @Column(name="imp_uid")
    private int impUid;//pg결제번호


}