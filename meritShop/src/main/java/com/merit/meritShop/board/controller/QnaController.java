package com.merit.meritShop.board.controller;

import com.merit.meritShop.board.domain.Qna;
import com.merit.meritShop.board.dto.QnaDto;
import com.merit.meritShop.board.repository.QnaRepository;
import com.merit.meritShop.board.service.QnaService;
import com.merit.meritShop.user.domain.User;
import com.merit.meritShop.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    public String getQnas(Model model, @PageableDefault Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        pageable = PageRequest.of(page, 5);

        Page<QnaDto> listPage = qnaService.findAllOrOrderByQnaId(pageable);

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


    //문의사항 수정
    @GetMapping("/qnaModify/{qnaId}")
    public String modifyQna(@PathVariable Long qnaId, Model model, @CookieValue Long userId) {
        QnaDto qna = qnaService.qnaDetail(qnaId);
        if (qna != null) {
            User user = userRepository.findById(userId).get();
            model.addAttribute("role", user.getRole());
            model.addAttribute("qna", qnaService.qnaDetail(qnaId));
            return "qna/qnaModify";
        }
        return "redirect:/admin/qnas";
    }

    @GetMapping("/admin/qnaModify/{qnaId}")
    public String adminModifyQna(@PathVariable Long qnaId, Model model, @CookieValue Long userId) {
        QnaDto qna = qnaService.qnaDetail(qnaId);
        if (qna != null) {
            User user = userRepository.findById(userId).get();

            model.addAttribute("qna", qnaService.qnaDetail(qnaId));
            model.addAttribute("role", user.getRole());
            model.addAttribute("qna", qnaService.qnaDetail(qnaId));
            return "qna/adminQnaModify";
        }
        return "redirect:/admin/qnas";
    }

    @PostMapping("/qna/update")
    public String qnaUpdate(@CookieValue Long userId, QnaDto qnaDTO, RedirectAttributes re) {

        String result = qnaService.qnaModify(qnaDTO, userId);
        if (result == "success") {
            User user = userRepository.findById(userId).get();
            Long qnaId = qnaDTO.getQnaId();
            re.addAttribute("qnaId", qnaId);

            if (user.getRole().equals("ROLE_USER")) return "redirect:/qna/detail";
            else return "redirect:/admin/qna/detail";

        } else {
            return "redirect:/main";
        }
    }

    @GetMapping("/qna/delete")
    public String qnaDelete(@RequestParam Long qnaId, @CookieValue Long userId) {
        String result = qnaService.qnaDelete(qnaId);
        User user = userRepository.findById(userId).get();

        if (user.getRole().equals("ROLE_USER")) return "redirect:/user/qnas";
        else return "redirect:/admin/qnas";

    }

    @GetMapping("/qna/detail")
    public String QnaDetail(Model model, @RequestParam Long qnaId) {
        QnaDto qna = qnaService.qnaDetail(qnaId);
        if (qna == null) {
            return "redirect:/admin/qnas";
        }

        model.addAttribute("qna", qna);
        return "qna/qnaDetail";
    }

    @GetMapping("/admin/qna/detail")
    public String adminQnaDetail(Model model, @RequestParam Long qnaId) {
        QnaDto qna = qnaService.qnaDetail(qnaId);
        if (qna == null) {
            return "redirect:/admin/qnas";
        }

        model.addAttribute("qna", qna);
        return "qna/adminQnaDetail";
    }
}
