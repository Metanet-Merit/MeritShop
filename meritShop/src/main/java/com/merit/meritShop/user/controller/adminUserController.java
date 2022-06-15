package com.merit.meritShop.user.controller;

import com.merit.meritShop.user.dto.UserViewDto;
import com.merit.meritShop.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/admin")
@Controller
public class adminUserController {

    private final UserService userService;

    @GetMapping("/user/list")
    public String userList(Model model, @PageableDefault Pageable pageable){
        Page<UserViewDto> userList = userService.getAllUserInfo(pageable);
        model.addAttribute("userList", userList);
        log.debug("총 element 수 : {}, 전체 page 수 : {}, 페이지에 표시할 element 수 : {}, 현재 페이지 index : {}, 현재 페이지의 element 수 : {}",
                userList.getTotalElements(), userList.getTotalPages(), userList.getSize(),
                userList.getNumber(), userList.getNumberOfElements());

        return "admin/user/userList";
    }

    @GetMapping("/user/search")
    public String search(String keyword, Model model, @PageableDefault(direction = Sort.Direction.DESC) Pageable pageable) {
        Page<UserViewDto> searchList = userService.search(keyword, pageable);
        model.addAttribute("userList", searchList);
        return "admin/user/userList";
    }


    @GetMapping("/user/detail")
    public String userDetail(Model model, @RequestParam Long userId){
        UserViewDto user = userService.getUserDetail(userId);
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
