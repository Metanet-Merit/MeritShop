package com.merit.meritShop.board.service;

import com.merit.meritShop.board.domain.Notice;
import com.merit.meritShop.board.repository.NoticeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        try{
            Optional<Notice> optionalNotice=noticeRepository.findById(NoticeId);
            if(optionalNotice.isPresent()){
                Notice notice=optionalNotice.get();
                return notice;
            }
            else{
                return null;
            }
        }catch(Exception e){
            return null;
        }
    }

    //글 수정
    public String noticeModify(Long noticeId,String noticeTitle,String noticeContent){
        try{
            Notice notice = noticeRepository.findById(noticeId).get();
            notice.setTitle(noticeTitle);
            notice.setContent(noticeContent);
            noticeRepository.save(notice);
            return "Success";

        }catch(Exception e){
            return "err";
        }
    }

    //글 삭제
    public void noticeDelete(Long noticeId) {

        noticeRepository.deleteById(noticeId);
    }
}
