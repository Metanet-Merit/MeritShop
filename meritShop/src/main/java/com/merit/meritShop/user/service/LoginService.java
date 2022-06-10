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

@RequiredArgsConstructor
@Service
public class LoginService {
    private final UserRepository userRepository;

    @Autowired
    JwtUtil jwtTokenProvider;

    public JwtResponseDto login (UserSignInDto userSignInDto) {
        User user = userRepository.findByEmail(userSignInDto.getEmail());
        UserToken usertoken = new UserToken(user);
        String token = jwtTokenProvider.generateToken(usertoken);
        JwtResponseDto jwtResponseDto = new JwtResponseDto(token, user.getUserId(), user.getEmail(), user.getUserName());
        return jwtResponseDto;
    }
}
