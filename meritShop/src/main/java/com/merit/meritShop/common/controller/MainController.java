package com.merit.meritShop.common.controller;

import com.merit.meritShop.item.domain.Item;
import com.merit.meritShop.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final ItemRepository itemRepository;

    @GetMapping("/main")
    public String getIndex(Model model)
    {
        PageRequest pageRequest =  PageRequest.of(0,8, Sort.by(Sort.Direction.DESC,"createdDate"));
        Page<Item> itemList = itemRepository.findAll(pageRequest);
        for(Item i : itemList){
            System.out.println(i);
        }
        model.addAttribute("items",itemList);

        return "mainPage/index";
    }

    @GetMapping("/admin/itemList")
    public String getAdminItemList(){
        return "AdminItemList";
    }
}
