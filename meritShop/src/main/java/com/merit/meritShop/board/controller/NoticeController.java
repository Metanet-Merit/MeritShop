package com.merit.meritShop.board.controller;

import com.merit.meritShop.board.domain.Notice;
import com.merit.meritShop.board.service.NoticeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@AllArgsConstructor
public class NoticeController {

    private NoticeService noticeService;

    /*목록*/
    @GetMapping("admin/notice/list")
    public String notieList(Model model) {
        List<Notice> noticeList = noticeService.noticeList();

        model.addAttribute("list", noticeService.noticeList());
        //user가 보는 noticeList,  return "main/notice";
        return "notice/noticeList";
    }

    /*글 작성*/
    @GetMapping("admin/notice/write")
    public String noticeWrite() {

        return "notice/noticeWrite";
    }
    @PostMapping("admin/notice/writepro")
    public String writePro(Notice notice) {

        noticeService.write(notice);

        return "redirect:list";
    }
    /*글 상세페이지*/
    @GetMapping("admin/notice/detail")
    public String noticeDetail(Model model, @RequestParam Long noticeId) {
        //log.info(noticeId.toString());
        Notice notice=noticeService.noticeDetail(noticeId);
        if(notice==null){
            return "redirect:list";
        }
        //log.info(notice.toString());
        model.addAttribute("notice",notice);
        return "notice/noticeDetail";
    }
    /*글 수정*/
    @GetMapping("admin/notice/modify/{noticeId}")
    public String noticeModify(@PathVariable("noticeId") Long noticeId,
                               Model model) {

        model.addAttribute("notice", noticeService.noticeDetail(noticeId));

        return "notice/noticeModify";
    }
    @PostMapping("admin/notice/update/{noticeId}")
    public String noticeUpdate(@PathVariable("noticeId") Long noticeId,
                               Notice notice) {

        String result= noticeService.noticeModify(noticeId,notice.getTitle(),notice.getContent());
        if (result == "Success"){
            return "redirect:/notice/list";
        }
        else {
            return "redirect:/notice/update/{noticeId}";
        }

    }

    /*글 삭제*/
    @GetMapping("admin/notice/delete")
    public String noticeDelete(Long noticeId) {
        noticeService.noticeDelete(noticeId);

        return "redirect:list";
    }
}
