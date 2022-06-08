package com.merit.meritShop.board.service;

import com.merit.meritShop.board.domain.Notice;
import com.merit.meritShop.board.repository.NoticeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class NoticeService {

    private NoticeRepository noticeRepository;

    //글 목록
    public List<Notice> noticeList() {
        return noticeRepository.findAll();
    }

    //글 작성
    public void write(Notice notice)  {

        noticeRepository.save(notice);
    }

    //글 상세 페이지
    public Notice noticeDetail(Long NoticeId) {

        return noticeRepository.findById(NoticeId).get();
    }

    //글 삭제
    public void noticeDelete(Long noticeId) {

        noticeRepository.deleteById(noticeId);
    }
    public String noticeModify(Long noticeId,String noticeTitle,String noticeContent){
        try{

            Notice notice=noticeRepository.findById(noticeId).get();
            notice.setTitle(noticeTitle);
            notice.setContent(noticeContent);
            noticeRepository.save(notice);
            return "Success";

        }catch(Exception e){
            return "err";
        }
    }
}
