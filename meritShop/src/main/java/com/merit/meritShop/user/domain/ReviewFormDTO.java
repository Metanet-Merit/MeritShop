package com.merit.meritShop.user.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewFormDTO {
    private Long userId;
    private Long orderItemId;
    private String orderItemName;
    private String content;
    private double rate;
    private String uuidName;
}
