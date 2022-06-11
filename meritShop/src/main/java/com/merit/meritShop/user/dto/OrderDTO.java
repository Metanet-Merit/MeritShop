package com.merit.meritShop.user.dto;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@DynamicInsert
@NoArgsConstructor
public class OrderDTO {

    Long userId;
    Long orderId;
    Integer count;
    Integer totalPrice;
    LocalDateTime orderDate;
}
