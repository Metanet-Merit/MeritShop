package com.merit.meritShop.user.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemsDTO {


    private String url;
    private Long orderId;
    private Long orderItemId;
    private Long itemId;
    private Long itemOptionId;
    private String orderItemName;
    private String orderItemOptionName;
    private int itemPrice;
    private int count;
    private int orderItemPrice;
    private String category;
    private boolean reviewed;
    private String address;
    private String recipient;
    LocalDateTime orderDate;


}
