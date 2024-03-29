package com.merit.meritShop.user.controller;

import com.merit.meritShop.user.dto.UserSignUpDto;
import com.merit.meritShop.user.service.SignUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RequiredArgsConstructor
@Controller
public class SignUpController {
        private final SignUpService signUpService;

        @GetMapping("/signup")
        public String signupForm(){
                return "login/form-bootstrap";
        }

        @PostMapping("/signup")
        public String register(UserSignUpDto userSignUpDto){
                if (signUpService.isExist(userSignUpDto.getEmail(), "local"))
                        System.out.println("중복회원");
                signUpService.save(userSignUpDto);
                return "login/login";
        }
        @ResponseBody
        @PostMapping("/user/idCheck")
        public int validId(@RequestParam("id") String email) {
                return signUpService.idCheck(email);
        }
}
