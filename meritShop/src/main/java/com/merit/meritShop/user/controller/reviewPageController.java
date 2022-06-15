package com.merit.meritShop.user.controller;

import com.merit.meritShop.user.dto.ReviewFormDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class reviewPageController {

    @GetMapping("/reviewForm/{orderItemId}")
    public String reviewPage(@PathVariable Long orderItemId, @ModelAttribute ReviewFormDTO reviewFormDTO, Model model){
        model.addAttribute("orderItemId",orderItemId);
        return "myPage/reviewForm";

    }
    @GetMapping("/reviewPage")
    public String reviewPage(Model model){
        return "myPage/review";

    }
}
