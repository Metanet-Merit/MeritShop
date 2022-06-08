package com.merit.meritShop.user.service;

import com.merit.meritShop.common.domain.Result;
import com.merit.meritShop.common.domain.ResultCode;
import com.merit.meritShop.item.repository.ItemRepository;
import com.merit.meritShop.order.Repository.OrderItemRepository;
import com.merit.meritShop.order.domain.OrderItem;
import com.merit.meritShop.user.domain.Review;
import com.merit.meritShop.user.domain.ReviewFormDTO;
import com.merit.meritShop.user.domain.User;
import com.merit.meritShop.user.repository.ReviewRepository;
import com.merit.meritShop.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class ReviewService {

    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    OrderItemRepository orderItemRepository;


    public Result<Map<String, Object>> getReviews(Long userId) {

        try {
            Map<String, Object> map = new HashMap<>();
            Optional<User> optionalUser=userRepository.findById(userId);
            User user=optionalUser.get();
            List<Review> reviewList = reviewRepository.findReviewByUser(user);
            List<ReviewFormDTO> reviewFormDTOList = new ArrayList<>();

            for (Review review : reviewList) {
                String itemName = review.getOrderItem().getItem().getItemName();
                ReviewFormDTO reviewFormDTO = new ReviewFormDTO();

                reviewFormDTO.setContent(review.getContent());
                reviewFormDTO.setOrderItemName(itemName);
                reviewFormDTOList.add(reviewFormDTO);

            }
            map.put("reviews", reviewFormDTOList);
            return ResultCode.Success.result(map);
        } catch (Exception e) {
            return ResultCode.DB_ERROR.result();
        }

    }

    public Result<Review> addReview(ReviewFormDTO reviewFormDTO){
        return addReview(reviewFormDTO.getUserId(),reviewFormDTO.getOrderItemId(),reviewFormDTO.getContent(),reviewFormDTO.getRate());
    }

    public Result<Review> addReview(Long userId,Long orderItemId,String content,double rate){
        try{

            Optional<User> optionalUser=userRepository.findById(userId);
            Optional<OrderItem> optionalOrderItem=orderItemRepository.findById(orderItemId);
            if(optionalOrderItem.isEmpty() && optionalUser.isEmpty()){
                return ResultCode.USER_NOT_EXISTS.result();
            }
            User user=optionalUser.get();
            OrderItem orderItem=optionalOrderItem.get();

            Review review=Review.builder()
                    .user(user)
                    .content(content)
                    .orderItem(orderItem)
                    .rate(rate)
                    .build();

            reviewRepository.save(review);
            return ResultCode.Success.result();

        }catch(Exception e){
            return ResultCode.ETC_ERROR.result();
        }
    }



}