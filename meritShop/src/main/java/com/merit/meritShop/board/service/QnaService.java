package com.merit.meritShop.board.service;

import com.merit.meritShop.board.domain.Notice;
import com.merit.meritShop.board.domain.Qna;
import com.merit.meritShop.board.domain.QnaDTO;
import com.merit.meritShop.board.repository.QnaRepository;
import com.merit.meritShop.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class QnaService {

    @Autowired
    QnaRepository qnaRepository;
    //문의사항 목록_admin
   public List<Qna> qnaList() {
       return qnaRepository.findAll();
   }
   //내가 작성한 문의사항 목록_user


   //공지사항 등록_user
   public String writeQnA(Qna qna) {
       try{
           qna.setReplied(false);
           qna.setRegisterDate(LocalDateTime.now());
           qnaRepository.save(qna);
           return "success";
       } catch(Exception e) {
           return "dbErr";
       }
   }
   //문의사항 답변 등록_admin
   public String reply(QnaDTO qnaDTO) {
       Long qnaId=qnaDTO.getQnaId();
       String reply_content=qnaDTO.getReply();
       try {
           Optional<Qna> optionalQna= qnaRepository.findById(qnaId);

            if(optionalQna.isPresent()) {
                Qna qna=optionalQna.get();
                qna.setReply(reply_content);
                qna.setReplied(true);
                qnaRepository.save(qna);
                return "success";
            } else {
                return "qna_not_exist_err";
            }
       } catch (Exception e) {
           return "dbErr";
       }
   }
   //문의사항 수정_user
    public String qnaModify(QnaDTO qnaDTO) {
       try {
           Optional<Qna> optionalQna=qnaRepository.findById(qnaDTO.getQnaId());

           if(optionalQna.isPresent()) {
               Qna qna=optionalQna.get();

               qna.setContent(qnaDTO.getContent());
               qna.setReply(qnaDTO.getReply());
               qnaRepository.save(qna);

               return "success";
           } else{
               return "qna_not_exist";
           }
       } catch(Exception e){
           return "dbErr";
       }

    }
    //삭제
    public String qnaDelete(Long qnaId){
       try{
            Optional<Qna> optionalQna=qnaRepository.findById(qnaId);
            if(optionalQna.isPresent()) {
                Qna qna=optionalQna.get();
                qnaRepository.delete(qna);
                return "success";
            } else {
                return "qna_not_exist_err";
            }
       } catch (Exception e) {
            return "dbErr";
       }
    }

}
