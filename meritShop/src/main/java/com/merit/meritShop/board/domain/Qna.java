package com.merit.meritShop.board.domain;

import com.merit.meritShop.item.domain.Item;
import com.merit.meritShop.user.domain.User;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@DynamicInsert
@NoArgsConstructor
@Table(name="qna")
public class Qna {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="qna_id")
    private Long qnaId;

    private String title;

    @Lob
    private String content;

    @Lob
    private String reply;

    @Column(name="reg_date")
    private LocalDateTime registerDate;

    @Column(name="modify_date")
    private LocalDateTime modifyDate;

    private boolean replied;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="item_id")
    private Item item;
}
