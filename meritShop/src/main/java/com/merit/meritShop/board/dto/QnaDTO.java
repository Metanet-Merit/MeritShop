package com.merit.meritShop.board.dto;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QnaDTO {

    Long userId;
    Long itemId;
    Long qnaId;
    String title;
    String reply;
    String content;


}
