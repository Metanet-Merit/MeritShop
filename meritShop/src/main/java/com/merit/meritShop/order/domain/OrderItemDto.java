package com.merit.meritShop.order.domain;


import lombok.Data;

import java.io.Serializable;

@Data
public class OrderItemDto implements Serializable {
    private Long itemId;
    private Long itemOptionId;
    private int count;

    @Override
    public String toString() {
        return "OrderItemDto{" +
                " itemId=" + itemId +
                ", optionId=" + itemOptionId +
                ", count=" + count +
                '}';
    }
}
