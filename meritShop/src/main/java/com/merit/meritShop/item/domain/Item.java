package com.merit.meritShop.item.domain;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@DynamicInsert
@NoArgsConstructor
@Table(name="item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="item_id")
    private Long itemId;

    private String category;

    @Column(name="item_name")
    private String itemName;

    private int price;
    private String description;
    private ItemSellStatus status;
    private boolean shipping;
    private String imgUrl;

    private double rate;

    @OneToMany(mappedBy = "item")
    private List<ItemOption> opt = new ArrayList<>();


    public Item of(ItemFormDto dto){
        if(dto.getItemId()!=null){itemId = dto.getItemId();}
        itemName = dto.getName();
        category = dto.getCategory();
        description = dto.getDescription();
        status = dto.getStatus();
        price =dto.getPrice();
        return this;
    }

    public ItemFormDto toItemFormDto(){
        if(!opt.isEmpty()){
            List<ItemOptionDto> dtos  = new ArrayList<>();
            for(ItemOption op: opt){
                dtos.add(op.toItemOptionDto());
            }
            ItemFormDto dto = new ItemFormDto(itemId,itemName,category,price,description,status,dtos);
            return dto;
        }else {
            ItemFormDto dto = new ItemFormDto(itemId,itemName,category,price,description,status,new ArrayList<>());
            return dto;
        }
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemId=" + itemId +
                ", name='" + itemName + '\'' +
                ", category='" + category + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", status=" + status +
                ", imgUrl='" + imgUrl + '\'' +
                '}';


    }

}