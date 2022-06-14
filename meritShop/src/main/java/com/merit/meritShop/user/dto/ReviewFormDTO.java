package com.merit.meritShop.user.dto;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewFormDTO {
    private Long userId;
    private Long orderItemId;
    private String orderItemName;
    private String userName;
    private String content;
    private double rate;
    private String uuidName;
    private MultipartFile img;
    private String category;
    LocalDateTime reviewDate;
    LocalDateTime orderDate;
}
