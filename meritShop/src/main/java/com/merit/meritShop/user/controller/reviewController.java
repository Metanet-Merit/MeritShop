package com.merit.meritShop.user.controller;

import com.merit.meritShop.common.controller.AbstractController;
import com.merit.meritShop.common.domain.Result;
import com.merit.meritShop.user.domain.Review;
import com.merit.meritShop.user.dto.ReviewFormDTO;
import com.merit.meritShop.user.service.ReviewService;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@Controller
@RequestMapping("/myPage")
public class reviewController {


    @Autowired
    ReviewService reviewService;

    @ResponseBody
    @GetMapping("/reviews")
    public Result getReviews(@RequestParam Long userId) {
        //  , @CookieValue(name = "userId", required = false) Long useId) { //userId는 쿠키에서 가져오기
        return reviewService.getReviews(userId);
    }
/*
    @PostMapping("review")
    public Result addReview(@RequestBody ReviewFormDTO reviewFormDTO) {
        return reviewService.addReview(reviewFormDTO);
    }
  */

    @PostMapping("review/{itemId}")
    public String addReview(@ModelAttribute ReviewFormDTO reviewFormDTO
                            ,@PathVariable("itemId") Long itemId
                            ,@CookieValue("userId")Long userId) throws IOException {
        String storedFileName=reviewService.storeFile(reviewFormDTO.getImg());
        reviewFormDTO.setUuidName(storedFileName);
        reviewFormDTO.setUserId(userId);
        reviewFormDTO.setOrderItemId(itemId);
        Result<Review> result=reviewService.addReview(reviewFormDTO);
        if(result.getResultCode().getCode()==0){
            return "redirect:/myPage";
        }
        return "redirect:/user/login";
    }

    @ResponseBody
    @GetMapping("/itemReviews")
    public Result getItemReviews(@RequestParam Long itemId){
        return reviewService.getItemReviews(itemId);
    }





}
