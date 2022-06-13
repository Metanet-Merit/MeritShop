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

@Slf4j
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
        log.info("Aaaaaa");
        List<Qna> qnaList = qnaService.qnaList();

        model.addAttribute("list",qnaList);
        return "qna/adminQnaList";

    }

    //문의사항 등록_user
    @PostMapping("/qna")
    public String write(Qna qna, Model model) {
        String result = qnaService.writeQnA(qna);
        if(result == "success") {
            Long id=qna.getItem().getItemId();
            return "redirect: /item/{id}";
        }
        else {
            return "qna/qnaList";

        }
    }
    //문의사항 답변_admin
    @PostMapping("/reply")
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
