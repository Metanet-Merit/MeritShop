package com.merit.meritShop.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert //null값을 제외하고 쿼리문 실행
@Entity
@Table(name="user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="user_name")
    private String userName;

    @Column(name="intra_id")
    private String intra_id;

    @Column(name="phone_number")
    private String phoneNumber;

    @Column(name="user_email")
    private String email;

    private String sex;

    @ColumnDefault("ROLE_USER") //default값 적용
    private String type;

    @Column(name="pwd")
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
