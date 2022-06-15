package com.merit.meritShop.user.service;

import com.merit.meritShop.user.domain.User;
import com.merit.meritShop.user.dto.UserViewDto;
import com.merit.meritShop.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
@Service
public class UserService {
    private final UserRepository userRepository;

    public Page<UserViewDto> getAllUserInfo(Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        pageable = PageRequest.of(page, 5);
        Page<User> userList = userRepository.findAll(pageable);
        Page<UserViewDto> viewList = new UserViewDto().toDtoList(userList);
        return viewList;
    }

    public UserViewDto getUserDetail(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(
                "해당 유저가 없습니다. ID = " + id));
        UserViewDto view = UserViewDto.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .userName(user.getUserName())
                .role(user.getRole().equals("ROLE_USER") ? "일반 회원" : user.getRole().equals("ROLE_PREMIUM") ? "프리미엄 회원" : "블랙 회원")
                .expireDate(getRemainDay(user.getRemainDay()))
                .addr1(user.getAddr1())
                .addr2(user.getAddr2())
                .phoneNumber(user.getPhoneNumber())
                .point(user.getPoint())
                .zipcode(user.getZipcode())
                .sex(user.getSex())
                .build();
        return view;
    }
    //patch
    public void updateUser(Map<String, Object> patchMap, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException(
                "해당 유저가 없습니다. ID = " + userId));
        user.patch(patchMap);
    }
    //회원 검색 (+페이징)
    public Page<UserViewDto> search(String keyword, Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        pageable = PageRequest.of(page, 5);
        Page<User> userList = userRepository.findByUserNameContaining(keyword, pageable);
        Page<UserViewDto> viewList = new UserViewDto().toDtoList(userList);
        return viewList;
    }


    //구독 만료일 변환
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
