package com.merit.meritShop.user.service;

import com.merit.meritShop.common.config.JwtUtil;
import com.merit.meritShop.common.dto.JwtResponseDto;
import com.merit.meritShop.user.domain.User;
import com.merit.meritShop.user.domain.UserToken;
import com.merit.meritShop.user.dto.UserSignInDto;
import com.merit.meritShop.user.dto.UserSignUpDto;
import com.merit.meritShop.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class LoginService {
    private final UserRepository userRepository;

    @Autowired
    JwtUtil jwtTokenProvider;

    public JwtResponseDto login (UserSignInDto userSignInDto) {
        Optional<User> user = userRepository.findByEmailAndLoginType(userSignInDto.getEmail(), userSignInDto.getLoginType());
        if (user == null)
            System.out.println("회원 없음"); //이후에 예외처리해야함
        UserToken usertoken = new UserToken(user.get());

        String token = jwtTokenProvider.generateToken(usertoken);
        JwtResponseDto jwtResponseDto = new JwtResponseDto(token, user.get().getUserId(), user.get().getEmail(), user.get().getUserName());
        return jwtResponseDto;
    }


}
