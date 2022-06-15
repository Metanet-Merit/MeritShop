package com.merit.meritShop.cart.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {
    Long cartId;
    Long userId;
    Long itemId;
    Long itemOptionId;
    int count;


}
