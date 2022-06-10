package com.merit.meritShop.order.domain;


import lombok.Data;

@Data
public class OrderItemDto {
    private Long orderId;
    private Long itemId;
    private Long itemOptionId;
    private int count;

    @Override
    public String toString() {
        return "OrderItemDto{" +
                "orderId=" + orderId +
                ", itemId=" + itemId +
                ", optionId=" + itemOptionId +
                ", count=" + count +
                '}';
    }
}
