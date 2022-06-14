package com.merit.meritShop.board.controller;

import com.merit.meritShop.board.domain.Qna;
import com.merit.meritShop.board.dto.QnaDTO;
import com.merit.meritShop.board.service.QnaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class QnaController {
    @Autowired
    QnaService qnaService;

    //문의사항 목록_user
    @GetMapping("/user/qnas")
    public String getMyQnas(@RequestParam Long userId, Model model) {
        List<Qna> qnaList = qnaService.myQnaList(userId);

        model.addAttribute("myQnaList", qnaList);
        return "qna/myQnaList";
    }

    //문의사항 목록_admin
    @GetMapping("/admin/qnas")
    public String getQnas(Model model) {
        List<Qna> qnaList = qnaService.qnaList();

        model.addAttribute("list",qnaList);
        return "qna/itemQnaList";

    }

    //문의사항 등록_user
    @PostMapping("/user/qnaWrite/{itemId}")
    public String write(QnaDTO qnaDTO,@CookieValue("userId") Long userId) {

        String result = qnaService.writeQnA(qnaDTO,userId);
        Long itemId=qnaDTO.getItemId();
        if(result == "success") {
            return "redirect:/item/{itemId}";
        }
        else {
            return "qna/qnaList";
        }
    }
    //문의사항 등록_user
    @GetMapping("/user/qnaWrite/{itemId}")
    public String writePage(@PathVariable Long itemId,Model model) {
            model.addAttribute("itemId",itemId);
            return "qna/myQnaWrite";
    }

    //문의사항 답변_admin
    @PostMapping("/admin/reply")
    public String reply(QnaDTO qnaDTO,Model model){
        String result = qnaService.reply(qnaDTO);
        if (result == "success") {
            Long qnaId = qnaDTO.getQnaId();

            return "redirect:qna/detail?qnaId=qnaId";
        } else {
            model.addAttribute("err","err");
            return "qna/qnaList";
        }
    }
    //문의사항 수정
    @GetMapping("/qnaModify")
    public String modifyQna(QnaDTO qnaDTO) {
        String result = qnaService.qnaModify(qnaDTO);
        if(result == "success"){
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
        if(result == "success"){
            return "qna/qnaList";
        }
        else {
            return "qna/qnaList";
        }
    }
}
