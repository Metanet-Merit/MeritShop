package com.merit.meritShop.board.dto;

import com.merit.meritShop.board.domain.Notice;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class NoticeDto {
    private Long noticeId;
    private String title;
    private String content;
    private LocalDateTime reg_date;
    private LocalDateTime modify_date;

    public Notice toEntity() {
        Notice notice = Notice.builder()
                .noticeId(noticeId)
                .title(title)
                .content(content)
                .build();
        return notice;
    }

    @Builder
    public NoticeDto(Long noticeId, String title, String content, LocalDateTime reg_date, LocalDateTime modify_date) {
        this.noticeId = noticeId;
        this.title = title;
        this.content = content;
        this.reg_date = reg_date;
        this.modify_date = modify_date;
    }
}
