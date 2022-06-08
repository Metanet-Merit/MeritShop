package com.merit.meritShop.user.controller;

import com.merit.meritShop.common.controller.AbstractController;
import com.merit.meritShop.common.domain.Result;
import com.merit.meritShop.user.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/admin")
public class categoryController extends AbstractController {

    @Autowired
    CategoryService categoryService;

    @GetMapping("/category")
    @ResponseBody
    public JSONObject getCategories() {
        Result result=categoryService.getCategories();
        log.info(result.toString());
        return return2JSON(result);
    }

    @PostMapping("/category")
    @ResponseBody
    public JSONObject createCategory(@RequestBody JSONObject category) {

        Result result=categoryService.addCategory(category);
        log.info(result.toString());
        return return2JSON(result);
    }

}


