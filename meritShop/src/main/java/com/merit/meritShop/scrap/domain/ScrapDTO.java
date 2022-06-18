package com.merit.meritShop.scrap.domain;

import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScrapDTO {

    private Long scrapId;
    private Long itemId;
    private Long itemOptionId;
    private String itemName;
    private String itemOptionName;
    private int itemPrice;
    private String category;
    private Long userId;
    private String url;

}
