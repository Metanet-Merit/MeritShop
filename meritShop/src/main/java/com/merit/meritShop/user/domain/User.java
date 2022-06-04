package com.merit.meritShop.user.domain;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@DynamicInsert
@NoArgsConstructor
@Table(name="user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long userId;

    @Column(name="user_name")
    private String userName;

    @Column(name="intra_id")
    private String intraId;

    @Column(name="phone_number")
    private String phoneNumber;

    private String email;

    private String sex;

    @ColumnDefault("'ROLE_USER'") //default값 적용
    private String role;

    @Column(name="password")
    private String password;

    @Column(name="remain_day")
    private String remainDay;

    @ColumnDefault("false")
    private boolean premium;

    private int point;

    private String addr1;

    private String addr2;

    private String zipcode;
}
