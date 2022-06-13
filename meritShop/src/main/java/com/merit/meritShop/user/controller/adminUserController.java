package com.merit.meritShop.user.controller;

import com.merit.meritShop.user.domain.User;
import com.merit.meritShop.user.dto.UserAdminViewDto;
import com.merit.meritShop.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping(value = "/admin")
@Controller
public class adminUserController {

    private final UserService userService;

    @GetMapping("/user/list")
    public String userList(Model model){
        List<UserAdminViewDto> userList = userService.getAllUserInfo();
        model.addAttribute("userList", userList);
        return "admin/user/userList";
    }

    @GetMapping("/user/detail")
    public String userDetail(Model model, @RequestParam Long userId){
        UserAdminViewDto user = userService.getUserDetail(userId);
        model.addAttribute("user", user);
        return "admin/user/userDetail";
    }

    @PostMapping("/user/detail/{userId}")
    public String userModify(@RequestBody MultiValueMap<String,Object> patchMapDto, @PathVariable Long userId){
        String role;
        switch (patchMapDto.getFirst("role").toString()) {
            case "프리미엄 회원":
                role = "ROLE_PREMIUM";
                break;
            case "일반 회원":
                role = "ROLE_USER";
                break;
            case "블랙 회원":
                role = "ROLE_BLACK";
                break;
            default:
                throw new IllegalArgumentException("해당 column이 없습니다.");
        }
        Map<String, Object> patchMap = new HashMap<>();
        patchMap.put("role", role);
        patchMap.put("point", patchMapDto.getFirst("point"));
        userService.updateUser(patchMap, userId);
        return "redirect:/admin/user/list";
    }
}
