package com.merit.meritShop.user.controller;

import com.merit.meritShop.common.domain.Result;
import com.merit.meritShop.user.service.ScrapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/myPage")
public class ScrapController {

    @Autowired
    ScrapService scrapService;

    @GetMapping("/scrap")
    public Result getScraps(@RequestParam Long userId) {
        //  , @CookieValue(name = "userId", required = false) Long useId) { //userId는 쿠키에서 가져오기
        return scrapService.getScraps(userId);
    }



}
