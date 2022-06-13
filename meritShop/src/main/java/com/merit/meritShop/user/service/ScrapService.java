package com.merit.meritShop.user.service;

import com.merit.meritShop.common.domain.Result;
import com.merit.meritShop.common.domain.ResultCode;
import com.merit.meritShop.item.domain.Item;
import com.merit.meritShop.scrap.domain.Scrap;
import com.merit.meritShop.user.domain.User;
import com.merit.meritShop.user.dto.ScrapDTO;
import com.merit.meritShop.user.repository.ScrapRepository;
import com.merit.meritShop.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ScrapService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    ScrapRepository scrapRepository;
    public Result<Map<String, Object>> getScraps(Long userId) {

        try {
            Map<String, Object> map = new HashMap<>();
            Optional<User> optionalUser=userRepository.findById(userId);
            User user=optionalUser.get();
            List<Scrap> scrapList=scrapRepository.findScrapByUser(user);
            List<ScrapDTO> scrapDTOList = new ArrayList<>();

            for (Scrap scrap:scrapList) {

                Item item=scrap.getItem();
                ScrapDTO scrapDTO = ScrapDTO.builder()
                        .itemName(item.getItemName())
                        .itemId(item.getItemId())
                        .uuidName(item.getImgUrl())
                        .build();

                scrapDTOList.add(scrapDTO);

            }
            map.put("scraps", scrapDTOList);
            return ResultCode.Success.result(map);
        } catch (Exception e) {
            return ResultCode.DB_ERROR.result();
        }

    }
}
