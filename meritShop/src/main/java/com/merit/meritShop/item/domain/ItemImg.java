package com.merit.meritShop.item.domain;

import com.merit.meritShop.common.domain.BaseEntity;
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
public class ItemImg extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="item_img_id")
    private Long itemImgId;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="item_id")
    private Item item;

    private String originFileName;
    private String imgUrl;
    private String imgName;

    public void updateItemImg(String originFileName, String imgName, String imgUrl) {
        this.originFileName = originFileName;
        this.imgName = imgName;
        this.imgUrl = imgUrl;
    }
    public ItemImgDto toItemImgDto(){

        return new ItemImgDto(itemImgId,item.getItemId(),originFileName,imgName,imgUrl);
    }
}