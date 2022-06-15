package com.merit.meritShop.user.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Getter
@NoArgsConstructor
public class UserToken {

    private Long id;

    private String email;

    private String role;

    private String loginType;


    public UserToken(User entity) {
        this.id = entity.getUserId();
        this.email = entity.getEmail();
        this.role = entity.getRole();
        this.loginType = entity.getLoginType();
    }

}
