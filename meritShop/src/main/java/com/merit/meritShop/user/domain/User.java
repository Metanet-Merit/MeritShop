package com.merit.meritShop.user.domain;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.Map;

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

    @ColumnDefault("0")
    private int point;

    private String addr1;

    private String addr2;

    private String zipcode;

    private String loginType;

    public void patch(Map<String, Object> patchMap) {
        for(Map.Entry<String,Object> entry : patchMap.entrySet()){
            findKeyAndPatch(entry.getKey(), entry.getValue());
        }
    }

    public void findKeyAndPatch(String key, Object value) {
        switch (key) {
            case "role":
                this.role = value.toString();
                if (!value.toString().equals("ROLE_PREMIUM"))
                    this.remainDay = null;
                break;
            case "point":
                this.point = Integer.parseInt(value.toString());
                break;
            default:
                throw new IllegalArgumentException("해당 column이 없습니다.");
        }
    }
}
