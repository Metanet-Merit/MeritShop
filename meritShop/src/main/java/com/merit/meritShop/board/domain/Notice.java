package com.merit.meritShop.board.domain;

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
@Table(name="notice")
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="notice_id")
    private Long noticeId;

    private String title;

    @Lob
    private String content;

    @Column(name="reg_date")
    private LocalDateTime registerDate;

    @Column(name="modify_date")
    private LocalDateTime modifyDate;

    private int views;


}
