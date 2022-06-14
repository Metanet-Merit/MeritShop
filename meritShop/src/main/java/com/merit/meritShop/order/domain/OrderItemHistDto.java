package com.merit.meritShop.order.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemHistDto {

    private String imgUrl;
    private Long orderId;
    private Long itemId;
    private String itemName;
    private int itemPrice;
    private int count; // 수량
    private int orderItemPrice;
    private boolean reviewed;
    private String address;
    private String recipient;


}
