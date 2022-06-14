package com.merit.meritShop.user.dto;

import lombok.*;

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
}
