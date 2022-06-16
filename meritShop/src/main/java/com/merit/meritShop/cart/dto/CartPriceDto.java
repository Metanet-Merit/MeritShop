package com.merit.meritShop.cart.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartPriceDto {
    private int totalPrice;

    private int itemPrice;

    private int shipment;
    
    private int finalPrice; //총 예상 결제금액

}
