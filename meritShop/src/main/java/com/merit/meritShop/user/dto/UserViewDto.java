package com.merit.meritShop.user.dto;

import com.merit.meritShop.user.domain.User;
import lombok.*;
import org.springframework.data.domain.Page;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserViewDto {
    private Long userId;

    private String userName;

    private String email;

    private String loginType;

    private String role;

    private int point;

    private String expireDate;

    private String addr1;

    private String addr2;

    private String phoneNumber;

    private String sex;

    private String zipcode;

    public Page<UserViewDto> toDtoList(Page<User> user){
        Page<UserViewDto> userViewDtoPage = user.map(m -> UserViewDto.builder()
                .userId(m.getUserId())
                .email(m.getEmail())
                .userName(m.getUserName())
                .loginType(m.getLoginType())
                .role(m.getRole().equals("ROLE_USER") ? "일반 회원" : m.getRole().equals("ROLE_PREMIUM") ? "프리미엄 회원" : "블랙 회원")
                .expireDate(getRemainDay(m.getRemainDay()))
                .point(m.getPoint())
                .build());
        return userViewDtoPage;
    }
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
