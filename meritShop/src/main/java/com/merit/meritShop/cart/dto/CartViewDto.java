package com.merit.meritShop.cart.dto;

import com.merit.meritShop.item.domain.ItemOption;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartViewDto {
    long itemId;
    long itemOptionId;
    long cartId;
    String imgUrl; //item
    String itemName; //item
    int price; // item price
    int count; // cart
    String optionName;
    List<ItemOption> optionList = new ArrayList<>();
}
