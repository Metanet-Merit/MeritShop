package com.merit.meritShop.user.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemsDTO {
    LocalDateTime orderDate;
    String orderItemName;
    String url;
    Integer count;
    boolean reviewed;

}
