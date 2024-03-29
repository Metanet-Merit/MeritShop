package com.merit.meritShop.scrap.controller;

import com.merit.meritShop.common.domain.Result;
import com.merit.meritShop.scrap.domain.ScrapDTO;
import com.merit.meritShop.scrap.service.ScrapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/myPage")
public class ScrapController {

    @Autowired
    ScrapService scrapService;

    @GetMapping("/myScraps")
    public String getScraps(@CookieValue(name = "userId", required = false) Long userId, Model model) { //userId는 쿠키에서 가져오기
        Result result = scrapService.getScraps(userId);
        if (result.getResultCode().getCode() != 0) {
            return "redirect:/myPage";
        }
        model.addAttribute("result", result);
        return "myPage/myScrapPage";
    }


    @ResponseBody
    @PostMapping("/scrap")
    public Result addScrap(@RequestBody ScrapDTO scrapDTO) {
        return scrapService.addScrap(scrapDTO);

    }

    @ResponseBody
    @GetMapping("/scrap/count/{userId}")
    public String getScrapCount(@PathVariable Long userId) {
        return scrapService.count(userId).toString();
    }

    @ResponseBody
    @PostMapping("/scrap/delete/{scrapId}")
    public Result deleteScrap(@PathVariable Long scrapId,Model model){
        return scrapService.deleteScrap(scrapId);
    }
}
