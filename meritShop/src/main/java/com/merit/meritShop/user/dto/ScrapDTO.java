package com.merit.meritShop.user.dto;

import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScrapDTO {
    String itemName;
    Long itemId;
    String uuidName;
}
