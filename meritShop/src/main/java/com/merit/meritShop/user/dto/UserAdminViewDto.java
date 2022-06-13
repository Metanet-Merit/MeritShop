package com.merit.meritShop.user.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAdminViewDto {
    private Long userId;

    private String userName;

    private String email;

    private String loginType;

    private String role;

    private int point;

    private String expireDate;

    private String address1;

    private String address2;

    private String phone;
}
