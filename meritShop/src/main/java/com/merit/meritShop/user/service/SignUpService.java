package com.merit.meritShop.user.service;

import com.merit.meritShop.user.domain.User;
import com.merit.meritShop.user.dto.UserSignUpDto;
import com.merit.meritShop.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SignUpService {
    private final UserRepository userRepository;

    public User save(UserSignUpDto userSignUpDto) {
        System.out.println(userSignUpDto.toString());
        User user = userRepository.save(userSignUpDto.toEntity());
        return user;
    }
}
