package com.merit.meritShop.board.controller;

import com.merit.meritShop.board.domain.Qna;
import com.merit.meritShop.board.dto.QnaDto;
import com.merit.meritShop.board.repository.QnaRepository;
import com.merit.meritShop.board.service.QnaService;
import com.merit.meritShop.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
public class QnaController {
    @Autowired
    QnaService qnaService;
    @Autowired
    QnaRepository qnaRepository;
    @Autowired
    UserRepository userRepository;

    //문의사항 목록_user
    @GetMapping("/user/qnas")
    public String getMyQnas(@CookieValue(value = "userId", required = false) Long userId
            , Model model, @PageableDefault(size = 5) Pageable pageable) {
        if (userId == null) {
            return "redirect:/user/login";
        }

        Page<Qna> qnaList = qnaService.myQnaList(userId, pageable);
        int endPage = Math.min(qnaList.getTotalPages(), qnaList.getPageable().getPageNumber() + 4);
        int startPage = Math.max(1, qnaList.getPageable().getPageNumber() - 4);

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("myQnaList", qnaList);
        return "qna/myQnaList";
    }

    //문의사항 목록_admin
    @RequestMapping("/admin/qnas")
    public String getQnas(Model model, @PageableDefault(size = 5) Pageable pageable) {

        Page<QnaDto> listPage = qnaService.findAllOrOrderByQnaId(pageable);
        int startPage = Math.max(1, listPage.getPageable().getPageNumber() - 4);
        int endPage = Math.min(listPage.getTotalPages(), listPage.getPageable().getPageNumber() + 4);

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("listPage", listPage);

        return "qna/adminQnaList";
    }

    //문의사항 등록_user
    @GetMapping("/user/qnaWrite/{itemId}")
    public String writePage(@PathVariable("itemId") Long itemId, Model model) {

        model.addAttribute("itemId", itemId);

        return "qna/myQnaWrite";
    }

    @PostMapping("/user/qnaWrite/{itemId}")
    public String write(QnaDto qnaDto, @CookieValue("userId") Long userId) {

        String result = qnaService.writeQnA(qnaDto, userId);
        Long itemId = qnaDto.getItemId();
        if (result == "success") {
            return "redirect:/item/{itemId}";
        } else {
            return "qna/qnaList";
        }
    }

    //문의사항 답변_admin
    @PostMapping("/admin/reply")
    public String reply(QnaDto qnaDto, Model model) {
        String result = qnaService.reply(qnaDto);
        if (result == "success") {
            Long qnaId = qnaDto.getQnaId();

            return "redirect:qna/detail?qnaId=qnaId";
        } else {
            model.addAttribute("err", "err");
            return "qna/qnaList";
        }
    }

    //문의사항 수정
    @GetMapping("/qnaModify/{qnaId}")
    public String modifyQna(@PathVariable Long qnaId, Model model) {
        QnaDto qna = qnaService.qnaDetail(qnaId);
        if (qna != null) {
            model.addAttribute("qna", qnaService.qnaDetail(qnaId));
            return "qna/qnaModify";
        }
        return "redirect:/admin/qnas";
    }

    @PostMapping("/qna/update")
    public String noticeUpdate(QnaDto qnaDTO, RedirectAttributes re) {

        String result = qnaService.qnaModify(qnaDTO);
        if (result == "success") {
            Long qnaId=qnaDTO.getQnaId();
            re.addAttribute("qnaId",qnaId);
            return "redirect:/qna/detail";
        } else {
            return "redirect:/user/qnas";
        }
    }

    @GetMapping("/qnaDelete")
    public String qnaDelete(@RequestParam Long qnaId) {
        String result = qnaService.qnaDelete(qnaId);
        if (result == "success") {
            return "qna/qnaList";
        } else {
            return "qna/qnaList";
        }
    }

    @GetMapping("/qna/detail")
    public String adminQnaDetail(Model model, @RequestParam Long qnaId) {
        QnaDto qna = qnaService.qnaDetail(qnaId);
        if (qna == null) {
            return "redirect:/admin/qnas";
        }

        model.addAttribute("qna", qna);
        return "qna/qnaDetail";
    }
}
