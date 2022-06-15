package com.merit.meritShop.user.service;

import com.merit.meritShop.coupon.service.CouponService;
import com.merit.meritShop.user.domain.User;
import com.merit.meritShop.user.dto.UserSignInDto;
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
    private final CouponService couponService;

    public boolean save(UserSignUpDto userSignUpDto) {
        if (isExist(userSignUpDto.getEmail(), "local"))
            return false; //중복 회원가입
        User user = userRepository.save(userSignUpDto.toEntity());
        couponService.publishCouponToUser(user,1L);
        return true;
    }

    public boolean isExist(String email, String loginType) {
        boolean emailExist = userRepository.existsByEmail(email);
        boolean loginExist = userRepository.existsByLoginType(loginType);
        if (emailExist && loginExist)   return true;
        return false;
    }
    public UserSignInDto registerGoogle(ResponseEntity<String> googleEntity) {
        JSONObject jsonObject = new JSONObject(googleEntity.getBody());
        String email = jsonObject.getString("email");
        String password = jsonObject.getString("id");
        User entity = User.builder()
                .email(email)
                .password(password)
                .userName(jsonObject.getString("name"))
                .loginType("google")
                .build();
        //한번 회원등록하면 save하지말고 바로 로그인 절차로 이동
        if (!isExist(jsonObject.getString("email"), "google")){
            User user =userRepository.save(entity);
            couponService.publishCouponToUser(user,1L);
        }
       return new UserSignInDto(email, password, "google");
    }

    public UserSignInDto registerKakao(ResponseEntity<String> kakaoEntity) {
        JSONObject jsonObject = new JSONObject(kakaoEntity.getBody());
        JSONObject json = jsonObject.getJSONObject("kakao_account");
        String email = json.getString("email");
        String password = Long.toString(jsonObject.getLong("id"));
        User entity = User.builder()
                .email(email)
                .password(password)
                .userName(jsonObject.getJSONObject("properties").getString("nickname"))
                .loginType("kakao")
                .build();
        if (!isExist(json.getString("email"), "kakao")){
            User user =userRepository.save(entity);
            couponService.publishCouponToUser(user,1L);
        }
        return new UserSignInDto(email, password, "kakao");
    }

    public UserSignInDto registerNaver(ResponseEntity<String> naverEntity) {
        JSONObject jsonObject = new JSONObject(naverEntity.getBody());
        JSONObject json = jsonObject.getJSONObject("response");
        String email = json.getString("email");
        String password =json.getString("id");
        User entity = User.builder()
                .email(json.getString("email"))
                .password(json.getString("id"))
                .userName(json.getString("name"))
                .phoneNumber(json.getString("mobile"))
                .sex(json.getString("gender"))
                .loginType("naver")
                .build();
        if (!isExist(json.getString("email"), "naver")){
            User user = userRepository.save(entity);
            couponService.publishCouponToUser(user,1L);
        }
        return new UserSignInDto(email, password, "naver");
    }
}
