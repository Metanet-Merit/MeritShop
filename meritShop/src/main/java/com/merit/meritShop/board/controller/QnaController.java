package com.merit.meritShop.board.controller;

import com.merit.meritShop.board.domain.Notice;
import com.merit.meritShop.board.domain.Qna;
import com.merit.meritShop.board.domain.QnaDTO;
import com.merit.meritShop.board.service.QnaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class QnaController {
    @Autowired
    QnaService qnaService;

    //내 문의사항 목록
    @GetMapping("/qnas")
    public String getQnas(Qna qna,Model model){
        List<Qna> qnaList=qnaService.qnaList();

        model.addAttribute("list",qnaList);
        return "qna/qnaList";

    }
    //유저 문의사항 등록
    @PostMapping("/qna")
    public String write(Qna qna, Model model) {
        String result = qnaService.writeQnA(qna);
        if(result == "success") {
            Long id=qna.getItem().getItemId();
            return "redirect: /item/{id}";
        }
        else{
            return "qna/qnaList";

        }
    }



    //관리자 문의사항 답변
    @PostMapping("/reply")
    public String reply(QnaDTO qnaDTO,Model model){
        String result=qnaService.reply(qnaDTO);
        if (result=="success"){
            Long qnaId=qnaDTO.getQnaId();
            return "redirect:qna/detail?qnaId=qnaId";
        }else{
            model.addAttribute("err","err");
            return "qna/qnaList";
        }
    }

    @GetMapping("/qnaDelete")
    public String qnaDelete(@RequestParam Long qnaId){
        String result=qnaService.qnaDelete(qnaId);
        if(result=="success"){
            return "qna/qnaList";
        }
        else{
            return "qna/qnaList";
        }
    }

    @GetMapping("/qnaModify")
    public String modifyQna(QnaDTO qnaDTO){
        String result=qnaService.qnaModify(qnaDTO);
        if(result=="success"){
            return"qna/qnaList";
        }
        else{
            return"qna/qnaList";
        }
    }




}
