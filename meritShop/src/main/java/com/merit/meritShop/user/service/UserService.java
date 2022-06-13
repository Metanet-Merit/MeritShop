package com.merit.meritShop.user.service;

import com.merit.meritShop.user.domain.User;
import com.merit.meritShop.user.dto.UserAdminViewDto;
import com.merit.meritShop.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
@Service
public class UserService {
    private final UserRepository userRepository;

    public List<UserAdminViewDto> getAllUserInfo() {
        List<User> userList = userRepository.findAll();
        List<UserAdminViewDto> viewList = new ArrayList<>();
        for (User user : userList) {
            UserAdminViewDto view = UserAdminViewDto.builder()
                    .userId(user.getUserId())
                    .email(user.getEmail())
                    .userName(user.getUserName())
                    .loginType(user.getLoginType())
                    .role(user.getRole().equals("ROLE_USER") ? "일반 회원" : user.getRole().equals("ROLE_PREMIUM") ? "프리미엄 회원" : "블랙 회원")
                    .expireDate(getRemainDay(user.getRemainDay()))
                    .point(user.getPoint())
                    .build();
            viewList.add(view);
        }
        return viewList;
    }

    public UserAdminViewDto getUserDetail(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        User user = null;
        if (userOptional == null)
            System.out.println("회원 없음");
        else
            user = userOptional.get();
        UserAdminViewDto view = UserAdminViewDto.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .userName(user.getUserName())
                .role(user.getRole().equals("ROLE_USER") ? "일반 회원" : user.getRole().equals("ROLE_PREMIUM") ? "프리미엄 회원" : "블랙 회원")
                .expireDate(getRemainDay(user.getRemainDay()))
                .address1(user.getAddr1())
                .address2(user.getAddr2())
                .phone(user.getPhoneNumber())
                .point(user.getPoint())
                .build();
        return view;
    }

    public void updateUser(Map<String, Object> patchMap, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException(
                "해당 유저가 없습니다. ID = " + userId));
        user.patch(patchMap);
    }

    //구독 만료일 변환
    public String getRemainDay(String remain) {
        if (remain == null)
            return "해당없음";
        else {
            Date now = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(now);
            cal.add(Calendar.DATE, Integer.parseInt(remain));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(cal.getTime());
        }
    }
}
