package com.merit.meritShop.order.domain;

import com.merit.meritShop.coupon.domain.Coupon;
import com.merit.meritShop.user.domain.User;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@DynamicInsert
@NoArgsConstructor
@Table(name="orders")
public class Orders {
    @Id
    @Column(name="order_id")
    private Long orderId;

    @Column(name="order_date")
    private LocalDateTime orderDate;

    private String status;

    @Column(name="total_price")
    private int totalPrice;

    private String address;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @OneToMany(mappedBy = "orders",cascade = CascadeType.ALL)
    private List<OrderItem> orderItemList ;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="coupon_id")
    private Coupon coupon;


    public void updateTotalPrice(){
        int total =0;
        for(OrderItem item:orderItemList){
            total+= item.getOrderItemPrice();
        }
        this.totalPrice =total;
    }
}
