package com.merit.meritShop.board.service;

import com.merit.meritShop.board.domain.Notice;
import com.merit.meritShop.board.domain.Qna;
import com.merit.meritShop.board.dto.QnaDto;
import com.merit.meritShop.board.repository.QnaRepository;
import com.merit.meritShop.common.domain.Result;
import com.merit.meritShop.common.domain.ResultCode;
import com.merit.meritShop.item.domain.Item;
import com.merit.meritShop.item.repository.ItemRepository;
import com.merit.meritShop.order.domain.OrderItem;
import com.merit.meritShop.user.domain.Review;
import com.merit.meritShop.user.domain.User;
import com.merit.meritShop.user.dto.ReviewFormDTO;
import com.merit.meritShop.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class QnaService {

    @Autowired
    QnaRepository qnaRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ItemRepository itemRepository;

    //내가 작성한 문의사항 목록_user
    public Page<Qna> myQnaList(Long userId, Pageable pageable) {
        User user = userRepository.findById(userId).get();
        Page<Qna> qnaList = qnaRepository.findByUser(user, pageable);
        return qnaList;
    }

    public QnaDto qnaDetail(Long qnaId) {
        try {
            Optional<Qna> optionalQna = qnaRepository.findById(qnaId);
            if (optionalQna.isPresent()) {
                Qna qna = optionalQna.get();
                QnaDto qnaDto = QnaDto.builder()
                        .itemName(qna.getItem().getItemName())
                        .userName(qna.getUser().getUserName())
                        .replied(qna.isReplied())
                        .reply(qna.getReply())
                        .content(qna.getContent())
                        .qnaId(qna.getQnaId())
                        .title(qna.getTitle())
                        .role(qna.getUser().getRole())
                        .build();

                return qnaDto;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }


    //문의사항 목록_admin
    public Page<QnaDto> findAllOrOrderByQnaId(Pageable pageable) {
        Page<Qna> qnaPage = qnaRepository.findAll(pageable);

        Page<QnaDto> map = qnaPage.map(qna -> new QnaDto(qna.getUser()
                .getUserId(), qna.getItem().getItemId(), qna.getQnaId(),
                qna.getUser().getUserName(), qna.getItem().getItemName(), qna.getTitle(), qna.getReply(),
                qna.getContent(), qna.getModifyDate(), qna.getRegisterDate(), qna.isReplied(), qna.getUser().getRole()));
        return map;
    }


    //문의사항 등록_user
    public String writeQnA(QnaDto qnaDto, Long userId) {
        try {
            User user = userRepository.findById(userId).get();
            Item item = itemRepository.findById(qnaDto.getItemId()).get();
            Qna qna = new Qna();
            qna.setReplied(false);
            qna.setRegisterDate(LocalDateTime.now());
            qna.setContent(qnaDto.getContent());
            qna.setTitle(qnaDto.getTitle());
            qna.setUser(user);
            qna.setItem(item);
            qnaRepository.save(qna);
            return "success";
        } catch (Exception e) {
            return "dbErr";
        }
    }

    //문의사항 답변 등록_admin
    public String reply(QnaDto qnaDTO) {
        Long qnaId = qnaDTO.getQnaId();
        String reply_content = qnaDTO.getReply();
        try {
            Optional<Qna> optionalQna = qnaRepository.findById(qnaId);

            if (optionalQna.isPresent()) {
                Qna qna = optionalQna.get();
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
    public String qnaModify(QnaDto qnaDTO, Long userId) {
        try {
            Optional<Qna> optionalQna = qnaRepository.findById(qnaDTO.getQnaId());
            User user = userRepository.findById(userId).get();
            if (optionalQna.isPresent()) {
                Qna qna = optionalQna.get();

                if (user.getRole().equals("ROLE_USER")) {
                    qna.setTitle(qna.getTitle());
                    qna.setContent(qnaDTO.getContent());
                    qna.setModifyDate(LocalDateTime.now());

                } else {
                    if (!qna.isReplied()) qna.setReplied(true);
                    qna.setReply(qnaDTO.getReply());
                }
                qnaRepository.save(qna);
                return "success";
            } else {
                return "qna_not_exist";
            }
        } catch (Exception e) {
            return "dbErr";
        }

    }

    //문의사항 삭제
    public String qnaDelete(Long qnaId) {
        try {
            Optional<Qna> optionalQna = qnaRepository.findById(qnaId);
            if (optionalQna.isPresent()) {
                Qna qna = optionalQna.get();
                qnaRepository.delete(qna);

                return "success";
            } else {
                return "qna_not_exist_err";
            }
        } catch (Exception e) {
            return "dbErr";
        }
    }


    public Result<Map<String, Object>> getItemQnas(Long itemId) {

        try {
            Map<String, Object> map = new HashMap<>();
            Item item = itemRepository.findById(itemId).get();
            List<QnaDto> qnaDtos=new ArrayList<>();
            List<Qna> qnaList = qnaRepository.findAllByItem(item);

            for(Qna qna:qnaList){
                QnaDto qnaDto=QnaDto.builder()
                        .qnaId(qna.getQnaId())
                        .content(qna.getContent())
                        .itemId(qna.getItem().getItemId())
                        .itemName(qna.getItem().getItemName())
                        .replied(qna.isReplied())
                        .title(qna.getTitle())
                        .modifyDate(qna.getModifyDate())
                        .registerDate(qna.getRegisterDate())
                        .userName(qna.getUser().getUserName())
                        .reply(qna.getReply())
                        .userId(qna.getUser().getUserId()).build();

                qnaDtos.add(qnaDto);
            }

            map.put("qnas", qnaDtos);
            map.put("count", qnaList.size());
            return ResultCode.Success.result(map);
        } catch (Exception e) {
            return ResultCode.DB_ERROR.result();
        }

    }


}
