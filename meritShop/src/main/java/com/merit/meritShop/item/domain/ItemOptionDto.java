package com.merit.meritShop.item.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemOptionDto {

    private long optId;
    private String optionName;
    private int quantity;


}
