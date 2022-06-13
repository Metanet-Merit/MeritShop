package com.merit.meritShop.order.domain;

import lombok.Data;

import java.util.List;

@Data
public class PayFormDto {
    private List<OrderItemDto> orderItemDtoList;
    private Long orderId;
}
