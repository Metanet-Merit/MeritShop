package com.merit.meritShop.user.controller;

import com.merit.meritShop.common.controller.AbstractController;
import com.merit.meritShop.common.domain.Result;
import com.merit.meritShop.user.dto.ReviewFormDTO;
import com.merit.meritShop.user.service.ReviewService;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/myPage")
public class reviewController extends AbstractController {


    @Autowired
    ReviewService reviewService;

    @GetMapping("/reviews")
    @ResponseBody
    public JSONObject getReviews(@RequestParam Long userId) {
        Result result=reviewService.getReviews(userId);
        return return2JSON(result);
    }

    @PostMapping("review")
    @ResponseBody
    public JSONObject addReview(@RequestBody ReviewFormDTO reviewFormDTO){
        Result result=reviewService.addReview(reviewFormDTO);
        return return2JSON(result);
    }
}
