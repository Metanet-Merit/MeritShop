package com.merit.meritShop.user.domain;
import com.merit.meritShop.common.domain.BaseEntity;
import com.merit.meritShop.order.domain.OrderItem;
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
@Table(name="review")
public class Review extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="review_id")
    private Long reviewId;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="order_item_id")
    private OrderItem orderItem;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;
    double rate;

    @Lob
    String content;

    String originalFileName;
    String uuidName;

}