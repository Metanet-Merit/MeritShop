package com.merit.meritShop.board.controller;

import com.merit.meritShop.board.domain.Notice;
import com.merit.meritShop.board.repository.NoticeRepository;
import com.merit.meritShop.board.service.NoticeService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
public class NoticeController {

    private NoticeService noticeService;
    @Autowired
    NoticeRepository noticeRepository;

    /*목록*/
    @RequestMapping("/admin/notice/list")
    public String adminNoticeList(Model model, @PageableDefault(size = 5) Pageable pageable) {
        Page<Notice> listPage = noticeRepository.findAll(pageable);

        int startPage= Math.max(1,listPage.getPageable().getPageNumber()-4);
        int endPage=Math.min(listPage.getTotalPages(),listPage.getPageable().getPageNumber()+4);

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("listPage", listPage);

        return "notice/noticeList";
    }

    @RequestMapping("/main/notice")
    public String userNoticeList(Model model, @PageableDefault(size = 5) Pageable pageable) {
        Page<Notice> listPage = noticeRepository.findAll(pageable);

        int startPage = Math.max(1,listPage.getPageable().getPageNumber()-4);
        int endPage=Math.min(listPage.getTotalPages(),listPage.getPageable().getPageNumber()+4);

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("listPage", listPage);
        return "notice/userNoticeList";
    }

    /*글 작성*/
    @GetMapping("/admin/notice/write")
    public String noticeWrite() {

        return "notice/noticeWrite";
    }
    @PostMapping("/admin/notice/writepro")
    public String writePro(Notice notice) {

        noticeService.write(notice);

        return "redirect:list";
    }
    /*글 상세페이지*/
    @GetMapping("/admin/notice/detail")
    public String adminNoticeDetail(Model model, @RequestParam Long noticeId) {
        Notice notice = noticeService.noticeDetail(noticeId);
        noticeService.updateView(noticeId);
        if(notice == null){
            return "redirect:list";
        }
        //log.info(notice.toString());
        model.addAttribute("notice",notice);
        return "notice/noticeDetail";
    }
    @GetMapping("/main/notice/detail")
    public String userNoticeDetail(Model model, @RequestParam Long noticeId) {
        Notice notice = noticeService.noticeDetail(noticeId);
        noticeService.updateView(noticeId);
        if(notice == null) {
            return "redirect:list";
        }
        //log.info(notice.toString());
        model.addAttribute("notice",notice);
        return "notice/noticeDetail";
    }
    /*글 수정*/
    @GetMapping("/admin/notice/modify/{noticeId}")
    public String noticeModify(@PathVariable("noticeId") Long noticeId,
                               Model model) {

        model.addAttribute("notice", noticeService.noticeDetail(noticeId));

        return "notice/noticeModify";
    }
    @PostMapping("/admin/notice/update/{noticeId}")
    public String noticeUpdate(@PathVariable("noticeId") Long noticeId,
                               Notice notice) {

        String result= noticeService.noticeModify(noticeId,notice.getTitle(),notice.getContent());
        if (result == "Success") {
            return "redirect:/admin/notice/list";
        }
        else {
            return "redirect:/notice/update/{noticeId}";
        }

    }

    /*글 삭제*/
    @GetMapping("/admin/notice/delete")
    public String noticeDelete(Long noticeId) {
        noticeService.noticeDelete(noticeId);

        return "redirect:list";
    }
}
