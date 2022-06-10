package com.merit.meritShop.user.service;

import com.merit.meritShop.user.domain.User;
import com.merit.meritShop.user.dto.UserSignUpDto;
import com.merit.meritShop.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SignUpService {
    private final UserRepository userRepository;

    public User save(UserSignUpDto userSignUpDto) {
        User user = userRepository.save(userSignUpDto.toEntity());
        return user;
    }

    public void registerGoogle(ResponseEntity<String> googleEntity) {
        JSONObject jsonObject = new JSONObject(googleEntity.getBody());
        User entity = User.builder()
                .email(jsonObject.getString("email"))
                .password(jsonObject.getString("id"))
                .userName(jsonObject.getString("name"))
                .build();
        userRepository.save(entity);
    }

    public void registerKakao(ResponseEntity<String> kakaoEntity) {
        JSONObject jsonObject = new JSONObject(kakaoEntity.getBody());
        JSONObject json = jsonObject.getJSONObject("kakao_account");
        User entity = User.builder()
                .email(json.getString("email"))
                .password(Long.toString(jsonObject.getLong("id")))
                .userName(jsonObject.getJSONObject("properties").getString("nickname"))
                .build();
        userRepository.save(entity);
    }

    public void registerNaver(ResponseEntity<String> naverEntity) {
        JSONObject jsonObject = new JSONObject(naverEntity.getBody());
        JSONObject json = jsonObject.getJSONObject("response");
        User entity = User.builder()
                .email(json.getString("email"))
                .password(json.getString("id"))
                .userName(json.getString("name"))
                .phoneNumber(json.getString("mobile"))
                .sex(json.getString("gender"))
                .build();
        userRepository.save(entity);
    }
}
