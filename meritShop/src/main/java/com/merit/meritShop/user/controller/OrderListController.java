package com.merit.meritShop.user.controller;

import com.merit.meritShop.common.controller.AbstractController;
import com.merit.meritShop.common.domain.Result;
import com.merit.meritShop.user.service.OrderListService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
@RequestMapping("myPage/")
public class OrderListController extends AbstractController {

    @Autowired
    OrderListService orderListService;

    @GetMapping("/orders")
    @ResponseBody
    public JSONObject getOrders(@RequestParam Long userId){
          //  , @CookieValue(name = "userId", required = false) Long useId) { //userId는 쿠키에서 가져오기

        Result result=orderListService.getOders(userId);
        return return2JSON(result);
    }
    @GetMapping("/orderItems")
    @ResponseBody
    public JSONObject getOrderItems(@RequestParam Long orderId){
        Result result=orderListService.getOderItems(orderId);
        return return2JSON(result);
    }

}
