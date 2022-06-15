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

    private String userName;
    private String itemName;
    private Long userId;
    private Long orderId;
    private Integer count;
    private Integer totalPrice;
    private LocalDateTime orderDate;
    private String category;
    private boolean reviewed;
    private String address;
    private String recipient;
}
