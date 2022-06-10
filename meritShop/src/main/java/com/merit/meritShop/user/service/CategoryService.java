package com.merit.meritShop.user.service;

import com.merit.meritShop.common.domain.Result;
import com.merit.meritShop.common.domain.ResultCode;
import com.merit.meritShop.item.domain.Category;
import com.merit.meritShop.user.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    public Result<Category> addCategory(JSONObject category) {
        //log.info(jsonObject.toString());
        String categoryName = category.get("category").toString();
        return addCategory(categoryName);
    }

    public Result<Category> addCategory(String categoryName) {

        try {
            Optional<Category> optionalCategory = categoryRepository.findCategoryByCategoryName(categoryName);
            if (optionalCategory.isPresent()) {
                return ResultCode.CATEGORY_ALREADY_EXISTS.result();
            } else {
                Category category = new Category();
                category.setCategoryName(categoryName);
                categoryRepository.save(category);
                return ResultCode.Success.result();
            }
        } catch (Exception e) {
            return ResultCode.ETC_ERROR.result();

        }
    }

    public Result<Map<String, Object>> getCategories() {

        try {
            Map<String, Object> map = new HashMap<>();
            List<Category> categoryList = categoryRepository.findAll();
            List<String> categoryNameList = new ArrayList<>();

            for (Category category : categoryList) {
                String categoryName = category.getCategoryName();
                categoryNameList.add(categoryName);
            }

            map.put("categories", categoryNameList);
            return ResultCode.Success.result(map);

        } catch (Exception e) {
            return ResultCode.ETC_ERROR.result();
        }

    }
}
