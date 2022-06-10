package com.merit.meritShop.user.dto;

import com.merit.meritShop.user.domain.User;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSignUpDto {
    private String userName;

    private String sex;

    private String email;

    private String password;

    private String phoneNumber;

    private String addr1;

    private String addr2;

    private String zipcode;

    public User toEntity() {
        return User.builder()
                .userName(userName)
                .sex(sex)
                .email(email)
                .password(password)
                .phoneNumber(phoneNumber)
                .addr1(addr1)
                .addr2(addr2)
                .zipcode(zipcode)
                .build();

    }

}
