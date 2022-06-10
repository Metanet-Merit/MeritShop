package com.merit.meritShop.order.domain;
import com.merit.meritShop.item.domain.Category;
import com.merit.meritShop.item.domain.Item;
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
@Table(name="order_item")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="order_item_id")
    private Long orderItemId;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="item_id")
    private Item item;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="order_id")
    private Orders orders;


    private int count;

    @Column(name="order_item_price")
    private int orderItemPrice;
    private boolean reviewed;


}