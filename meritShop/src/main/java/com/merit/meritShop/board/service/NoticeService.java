package com.merit.meritShop.board.service;


import com.merit.meritShop.board.domain.Notice;
import com.merit.meritShop.board.domain.NoticeRepository;
import com.merit.meritShop.board.dto.NoticeDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class NoticeService {
    private NoticeRepository noticeRepository;

    @Transactional
    public List<NoticeDto> getNoticelist() {
        List<Notice> notices = noticeRepository.findAll();
        List<NoticeDto> noticeDtoList = new ArrayList<>();

        for ( Notice notice : notices) {
            NoticeDto noticeDto = NoticeDto.builder()
                    .noticeId(notice.getNoticeId())
                    .title(notice.getTitle())
                    .content(notice.getContent())
                    .reg_date(notice.getRegisterDate())
                    .build();

            noticeDtoList.add(noticeDto);
        }

        return noticeDtoList;
    }
}
