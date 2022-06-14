package com.merit.meritShop.user.controller;

import com.merit.meritShop.user.dto.UserViewDto;
import com.merit.meritShop.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping(value = "/myPage")
@Controller
public class MypageUserController {
    private final UserService userService;

    @GetMapping("/user")
    public String userMypage(HttpServletRequest request, Model model){
        Long id = getIdFromCookies(request.getCookies());
        if (id == null)
            return "login/login";
        else {
            UserViewDto user = userService.getUserDetail(id);
            model.addAttribute("user", user);
            return "myPage/user";
        }
    }

    @GetMapping("/user/modify")
    public String userModifyPage(HttpServletRequest request, Model model) {
        Long id = getIdFromCookies(request.getCookies());
        if (id == null)
            return "login/login";
        else {
            UserViewDto user = userService.getUserDetail(id);
            model.addAttribute("user", user);
            return "myPage/userModify";
        }
    }

    @PostMapping("/user/modify/{userId}")
    public String userModify(@RequestBody MultiValueMap<String,Object> patchMapDto, @PathVariable Long userId){
        Map<String, Object> patchMap = new HashMap<>();
        System.out.println("뭐냐고");
        for(Map.Entry<String, List<Object>> entry : patchMapDto.entrySet()){
            patchMap.put(entry.getKey(), entry.getValue().get(0));
        }
        userService.updateUser(patchMap, userId);
        return "redirect:/myPage/user";
    }

    public Long getIdFromCookies(Cookie[] cookies) {
        if (cookies == null)
            return null;
        for(Cookie c : cookies) {
            if (c.getName().equals("userId")) {
                return Long.parseLong(c.getValue());
            }
        }
        return null;
    }

}
