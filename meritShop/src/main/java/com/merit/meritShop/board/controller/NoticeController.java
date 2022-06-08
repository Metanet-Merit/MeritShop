package com.merit.meritShop.board.controller;

import com.merit.meritShop.board.domain.Notice;
import com.merit.meritShop.board.service.NoticeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@AllArgsConstructor
@Slf4j
public class NoticeController {

    private NoticeService noticeService;

    /*목록*/
    @GetMapping("notice/list")
    public String notieList(Model model) {
        log.info("aa");
        List<Notice> noticeList = noticeService.noticeList();

        model.addAttribute("list", noticeService.noticeList());
        return "notice/noticeList";
    }
    /*글 작성*/
    @GetMapping("notice/write")
    public String noticeWrite() {

        return "notice/noticeWrite";
    }
    @PostMapping("notice/writepro")
    public String writePro(Notice notice) {
        log.info("Aa");
        log.info(notice.toString());
        notice.setModifyDate(LocalDateTime.now());
        notice.setRegisterDate(LocalDateTime.now());
        noticeService.write(notice);

        return "redirect:list";
    }
    /*글 상세페이지*/
    @GetMapping("notice/detail")
    public String noticeDetail(Model model, @RequestParam Long noticeId) {
        //log.info(noticeId.toString());
        Notice notice=noticeService.noticeDetail(noticeId);
        //log.info(notice.toString());
        model.addAttribute("notice",notice);
        return "notice/noticeDetail";
    }
    /*글 수정*/
    @GetMapping("notice/modify/{noticeId}")
    public String noticeModify(@PathVariable("noticeId") Long noticeId,
                               Model model) {

        model.addAttribute("notice", noticeService.noticeDetail(noticeId));

        return "notice/noticeModify";
    }
    @PostMapping("notice/update/{noticeId}")
    public String noticeUpdate(@PathVariable("noticeId") Long noticeId,
                               Notice notice) {
        log.info(notice.toString());

        String result= noticeService.noticeModify(noticeId,notice.getTitle(),notice.getContent());
        if (result=="Success"){
            return "redirect:/notice/list";
        }
        else{
            log.info("aaa");
            return "redirect:/notice/update/{noticeId}";
        }

    }

    /*글 삭제*/
    @GetMapping("notice/delete")
    public String noticeDelete(Long noticeId) {
        noticeService.noticeDelete(noticeId);

        return "redirect:list";
    }
}
