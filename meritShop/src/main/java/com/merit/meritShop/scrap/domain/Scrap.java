package com.merit.meritShop.scrap.domain;

import com.merit.meritShop.item.domain.Item;
import com.merit.meritShop.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="scrap")
public class Scrap {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne               //찜할 '상품'이 없을 경우는 거의 없으니 이쪽에서 아이템을 관리
    @JoinColumn(name="item_id") // 해당 '상품'에 대한 찜이 없는 경우는 많다.
    private Item item;
}
