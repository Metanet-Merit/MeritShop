package com.merit.meritShop.board.controller;

import com.merit.meritShop.board.domain.Qna;
import com.merit.meritShop.board.dto.QnaDto;
import com.merit.meritShop.board.repository.QnaRepository;
import com.merit.meritShop.board.service.QnaService;
import com.merit.meritShop.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public String getMyQnas(@RequestParam Long userId, Model model) {
        List<Qna> qnaList = qnaService.myQnaList(userId);

        model.addAttribute("myQnaList", qnaList);
        return "qna/myQnaList";
    }

    //문의사항 목록_admin
    @RequestMapping("/admin/qnas")
    public String getQnas(Model model, @PageableDefault(size = 5) Pageable pageable) {

        Page<QnaDto> listPage=qnaService.findAllOrOrderByQnaId(pageable);
        int startPage = Math.max(1,listPage.getPageable().getPageNumber()-4);
        int endPage=Math.min(listPage.getTotalPages(),listPage.getPageable().getPageNumber()+4);

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("listPage", listPage);

        return "qna/adminQnaList";
    }

    //문의사항 등록_user
    @GetMapping("/user/qnaWrite/{itemId}")
    public String writePage(@PathVariable Long itemId, Model model) {

        model.addAttribute("itemId",itemId);

        return "qna/myQnaWrite";
    }

    @PostMapping("/user/qnaWrite/{itemId}")
    public String write(QnaDto qnaDto, @CookieValue("userId") Long userId) {

        String result = qnaService.writeQnA(qnaDto, userId);
        Long itemId = qnaDto.getItemId();
        if(result == "success") {
            return "redirect:/item/{itemId}";
        }
        else {
            return "qna/qnaList";
        }
    }

    //문의사항 답변_admin
    @PostMapping("/admin/reply")
    public String reply(QnaDto qnaDto, Model model){
        String result = qnaService.reply(qnaDto);
        if (result == "success") {
            Long qnaId = qnaDto.getQnaId();

            return "redirect:qna/detail?qnaId=qnaId";
        } else {
            model.addAttribute("err","err");
            return "qna/qnaList";
        }
    }
    //문의사항 수정
    @GetMapping("/qnaModify")
    public String modifyQna(QnaDto qnaDto) {
        String result = qnaService.qnaModify(qnaDto);
        if(result == "success") {
            return"qna/qnaList";
        }
        else {
            return"qna/qnaList";
        }
    }
    //문의사항 삭제
    @GetMapping("/qnaDelete")
    public String qnaDelete(@RequestParam Long qnaId) {
        String result = qnaService.qnaDelete(qnaId);
        if(result == "success") {
            return "qna/qnaList";
        }
        else {
            return "qna/qnaList";
        }
    }
}
