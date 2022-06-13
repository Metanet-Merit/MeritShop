package com.merit.meritShop.user.controller;

import com.merit.meritShop.user.domain.User;
import com.merit.meritShop.user.dto.UserAdminViewDto;
import com.merit.meritShop.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping(value = "/admin")
@Controller
public class adminUserController {

    private final UserService userService;

    @GetMapping("/user/list")
    public String index(Model model){
        List<UserAdminViewDto> userList = userService.getAllUserInfo();
        model.addAttribute("userList", userList);
        return "admin/user/userList";
    }
}
