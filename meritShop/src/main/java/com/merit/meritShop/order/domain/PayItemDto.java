package com.merit.meritShop.order.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PayItemDto {
    private Long ItemId;
    private Long ItemOptionId;
    private String itemName;
    private int count;
    private int price;

}
