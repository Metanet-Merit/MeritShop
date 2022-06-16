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

    private Long userId;
    private Long itemId;
    private Long qnaId;
    private String userName;
    private String itemName;
    private String title;
    private String reply;
    private String content;
    private LocalDateTime modifyDate;
    private LocalDateTime registerDate;
    private boolean replied;
    private String role;
}
