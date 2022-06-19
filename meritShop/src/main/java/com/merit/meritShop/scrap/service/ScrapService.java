package com.merit.meritShop.scrap.service;

import com.merit.meritShop.common.domain.Result;
import com.merit.meritShop.common.domain.ResultCode;
import com.merit.meritShop.item.domain.Item;
import com.merit.meritShop.item.domain.ItemOption;
import com.merit.meritShop.item.repository.ItemOptionRepository;
import com.merit.meritShop.item.repository.ItemRepository;
import com.merit.meritShop.scrap.domain.Scrap;
import com.merit.meritShop.user.domain.User;
import com.merit.meritShop.user.dto.ScrapDTO;
import com.merit.meritShop.scrap.repository.ScrapRepository;
import com.merit.meritShop.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CookieValue;

import java.util.*;

@Service
public class ScrapService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    ScrapRepository scrapRepository;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    ItemOptionRepository itemOptionRepository;

    public Result<Map<String, Object>> getScraps(Long userId) {

        try {
            Map<String, Object> map = new HashMap<>();
            Optional<User> optionalUser = userRepository.findById(userId);
            User user = optionalUser.get();
            List<Scrap> scrapList = scrapRepository.findScrapByUser(user);
            List<ScrapDTO> scrapDTOList = new ArrayList<>();

            for (Scrap scrap : scrapList) {

                Item item = scrap.getItem();

                ItemOption itemOption= itemOptionRepository.findItemOptionByItem(item).get();
                ScrapDTO scrapDTO = ScrapDTO.builder()
                        .itemName(item.getItemName())
                        .itemId(item.getItemId())
                        .url(item.getImgUrl())
                        .itemOptionName(itemOption.getOptName())
                        .category(item.getCategory())
                        .url(item.getImgUrl())
                        .itemPrice(item.getPrice())
                        .build();

                scrapDTOList.add(scrapDTO);

            }
            map.put("scraps", scrapDTOList);
            return ResultCode.Success.result(map);
        } catch (Exception e) {
            return ResultCode.DB_ERROR.result();
        }

    }

    public Result<Scrap> addScrap(ScrapDTO scrapDTO) {
        return addScrap(scrapDTO.getUserId(), scrapDTO.getItemId());
    }

    public Result<Scrap> addScrap(Long userId, Long itemId) {

        try {
            if (userId == null) return ResultCode.NOT_LOGIN.result();

            User user = userRepository.findById(userId).get();
            Item item = itemRepository.findById(itemId).get();
            List<Scrap> scrapList = scrapRepository.findScrapByUser(user);

            for (Scrap scrap : scrapList) {
                if (scrap.getItem().getItemId() == itemId) {
                    return ResultCode.SCRAP_ALREADY_EXISTS.result();
                }
            }
            Scrap scrap = Scrap.builder()
                    .user(user)
                    .item(item)
                    .build();

            scrapRepository.save(scrap);
            return ResultCode.Success.result();
        } catch (Exception e) {
            return ResultCode.DB_ERROR.result();
        }

    }

    public Long count(Long userId){
        if(userId==null) return 0L;
        User user=userRepository.findById(userId).get();
        return scrapRepository.countByUser(user);
    }
}
