package com.merit.meritShop.item.domain;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;


@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@DynamicInsert
@NoArgsConstructor
@Table(name="item_img")
public class ItemImg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="item_img_id")
    private Long itemImgId;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="item_id")
    private Item item;

    private String originalFileName;
    private String imgUrl;
    private String imgName;


}