package com.merit.meritShop.board.service;

import com.merit.meritShop.board.domain.Qna;
import com.merit.meritShop.board.dto.QnaDTO;
import com.merit.meritShop.board.repository.QnaRepository;
import com.merit.meritShop.item.domain.Item;
import com.merit.meritShop.item.repository.ItemRepository;
import com.merit.meritShop.user.domain.User;
import com.merit.meritShop.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class QnaService {

    @Autowired
    QnaRepository qnaRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ItemRepository itemRepository;

    //내가 작성한 문의사항 목록_user
    public List<Qna> myQnaList(Long userId) {
        User user=userRepository.findById(userId).get();
        List<Qna> qnaList=qnaRepository.findByUser(user);
        return qnaList;
    }

    //문의사항 목록_admin
    public List<Qna> qnaList() {

        return qnaRepository.findAll();
   }

   //문의사항 등록_user
   public String writeQnA(QnaDTO qnaDTO,Long userId) {
       try{
           User user=userRepository.findById(userId).get();
           Item item=itemRepository.findById(qnaDTO.getItemId()).get();
           Qna qna=new Qna();
           qna.setReplied(false);
           qna.setRegisterDate(LocalDateTime.now());
           qna.setContent(qnaDTO.getContent());
           qna.setTitle(qnaDTO.getTitle());
           qna.setUser(user);
           qna.setItem(item);
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
   //문의사항 수정
    public String qnaModify(QnaDTO qnaDTO) {
       try {
           Optional<Qna> optionalQna=qnaRepository.findById(qnaDTO.getQnaId());

           if(optionalQna.isPresent()) {
               Qna qna=optionalQna.get();
               if(qna.getUser().getRole().equals("ROLE_USER")) {
                   qna.setTitle(qna.getTitle());
                   qna.setContent(qnaDTO.getContent());
                   qna.setModifyDate(LocalDateTime.now());
               } else {
                   qna.setReply(qnaDTO.getReply());
               }
               qnaRepository.save(qna);
               return "success";
           } else{
               return "qna_not_exist";
           }
       } catch(Exception e){
           return "dbErr";
       }

    }
    //문의사항 삭제
    public String qnaDelete(Long qnaId){
       try{
            Optional<Qna> optionalQna=qnaRepository.findById(qnaId);
            if(optionalQna.isPresent()) {
                Qna qna=optionalQna.get();
                if(qna.getUser().getRole().equals("ROLE_USER")) {
                    qnaRepository.delete(qna);
                } else {
                    qna.setReply("");
                    qnaRepository.save(qna);
                }
                return "success";
            } else {
                return "qna_not_exist_err";
            }
       } catch (Exception e) {
            return "dbErr";
       }
    }

}
