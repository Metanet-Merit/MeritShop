package com.merit.meritShop.cart.domain;
import com.merit.meritShop.item.domain.Item;
import com.merit.meritShop.user.domain.User;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;


@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@DynamicInsert
@NoArgsConstructor
@Table(name="cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="item_id")
    private Item item;

    private int count;//수량

     /*@ElementCollection
    @CollectionTable(
            name = "cart_line"
    )
    @MapKeyColumn(name = "map_key")
    private Map<Long, Cart> cart = new HashMap<>();

    public  Cart(Long userId) {
        this.user.getUserId() = userId;
    }*/


}
