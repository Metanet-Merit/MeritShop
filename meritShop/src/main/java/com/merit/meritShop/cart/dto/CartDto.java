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

    @Override
    public String toString() {
        return "CartDto{" +
                "cartId=" + cartId +
                ", userId=" + userId +
                ", itemId=" + itemId +
                ", itemOptionId=" + itemOptionId +
                ", count=" + count +
                '}';
    }
}
