package com.merit.meritShop.user.service;

import com.merit.meritShop.user.domain.User;
import com.merit.meritShop.user.dto.UserAdminViewDto;
import com.merit.meritShop.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
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
                    .role(user.getRole().equals("ROLE_USER") ? "일반회원" : "프리미엄 회원")
                    .expireDate(getRemainDay(user.getRemainDay()))
                    .build();
            viewList.add(view);
        }
        return viewList;
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
