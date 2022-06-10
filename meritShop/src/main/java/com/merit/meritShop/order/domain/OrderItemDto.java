package com.merit.meritShop.order.domain;


import lombok.Data;

@Data
public class OrderItemDto {
    private Long itemId;
    private Long optionId;
    private int count;

    @Override
    public String toString() {
        return "OrderItemDto{" +
                "itemId=" + itemId +
                ", optionId=" + optionId +
                ", count=" + count +
                '}';
    }
}
