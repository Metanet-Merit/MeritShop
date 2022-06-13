package com.merit.meritShop.user.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemsDTO {
    String orderItemName;
    Integer count;
    boolean reviewed;

}
