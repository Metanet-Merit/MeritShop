package com.merit.meritShop.board.dto;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QnaDto {

    Long userId;
    Long itemId;
    Long qnaId;
    String userName;
    String title;
    String reply;
    String content;
    LocalDateTime modifyDate;
    LocalDateTime registerDate;

}
