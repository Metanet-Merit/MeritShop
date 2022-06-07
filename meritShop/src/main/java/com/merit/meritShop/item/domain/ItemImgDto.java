package com.merit.meritShop.item.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemImgDto {

    private long itemImgId;
    private long itemId;
    private String originFileName;
    private String imgName;
    private String imgUrl;
}
