package com.merit.meritShop.user.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSignInDto {
    private String email;

    private String password;

    private String loginType;
}
