package com.merit.meritShop.item.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemFormDto {
    private Long itemId;
    private String name;
    private String category;
    private int price;
    private String description;
    private ItemSellStatus status;

    private List<ItemOptionDto> options = new ArrayList<>();

    @Override
    public String toString() {
        return "ItemFormDto{" +
                "itemId=" + itemId +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }
}
