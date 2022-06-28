package com.merit.meritShop.scrap.domain;

import com.merit.meritShop.item.domain.Item;
import com.merit.meritShop.item.domain.ItemOption;
import com.merit.meritShop.user.domain.User;
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
@Table(name="scrap")
public class Scrap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="scrap_id")
    private Long scrapId;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(fetch=FetchType.LAZY) //찜할 '상품'이 없을 경우는 거의 없으니 이쪽에서 아이템을 관리
    @JoinColumn(name="item_id") // 해당 '상품'에 대한 찜이 없는 경우는 많다.
    private Item item;

    @ManyToOne(fetch=FetchType.LAZY) //찜할 '상품'이 없을 경우는 거의 없으니 이쪽에서 아이템을 관리
    @JoinColumn(name="item_option_id") // 해당 '상품'에 대한 찜이 없는 경우는 많다.
    private ItemOption itemOption;
}
