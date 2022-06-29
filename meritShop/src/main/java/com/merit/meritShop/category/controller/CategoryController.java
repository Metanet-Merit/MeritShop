package com.merit.meritShop.category.controller;

import com.merit.meritShop.item.domain.Item;
import com.merit.meritShop.item.repository.ItemRepository;
import com.merit.meritShop.category.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Slf4j
@Controller
public class CategoryController {
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    CategoryService categoryService;

    @GetMapping("/items/{category}")
    public String getIndex(@PathVariable("category") String category, Model model
            , @PageableDefault Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        pageable = PageRequest.of(page, 8);
        Page<Item> itemList = categoryService.getItemByCategory(category, pageable);
        model.addAttribute("category",category);
        model.addAttribute("items", itemList);
        model.addAttribute("name", category);
        return "mainPage/categoryIndex";
    }
}
