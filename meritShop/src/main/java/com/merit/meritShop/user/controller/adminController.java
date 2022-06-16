package com.merit.meritShop.user.controller;

import com.merit.meritShop.user.dto.UserViewDto;
import com.merit.meritShop.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class adminController {
    private final UserService userService;
    @GetMapping("/admin")
    public String index(Model model, @PageableDefault Pageable pageable){
        Page<UserViewDto> userList = userService.getAllUserInfo(pageable);
        model.addAttribute("userList", userList);

        return "admin/user/userList";
    }
}
