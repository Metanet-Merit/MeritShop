package com.merit.meritShop.common.controller;

import com.merit.meritShop.item.domain.Item;
import com.merit.meritShop.item.repository.ItemRepository;
import com.merit.meritShop.user.domain.User;
import com.merit.meritShop.user.dto.UserViewDto;
import com.merit.meritShop.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @GetMapping("/main")
    public String getIndex(Model model)
    {


        PageRequest pageRequest =  PageRequest.of(0,8, Sort.by(Sort.Direction.DESC,"createdDate"));
        Page<Item> itemList = itemRepository.findAll(pageRequest);
        model.addAttribute("items",itemList);

        return "mainPage/index";
    }

    @GetMapping("/main/search")
    public String search(String keyword, Model model, @PageableDefault(direction = Sort.Direction.DESC) Pageable pageable) {
        PageRequest pageRequest =  PageRequest.of(0,8, Sort.by(Sort.Direction.DESC,"createdDate"));
        Page<Item> itemList = itemRepository.findByItemNameContaining(keyword, pageRequest);
        model.addAttribute("items",itemList);
        return "mainPage/index";
    }

    @GetMapping(value = "/err/denied-page")
    public String accessDenied(){
        return "err/deniedPage";
    }

}
