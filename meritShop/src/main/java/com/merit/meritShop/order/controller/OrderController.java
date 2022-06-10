package com.merit.meritShop.order.controller;

import com.merit.meritShop.order.domain.OrderItemDto;
import com.merit.meritShop.order.domain.Orders;
import com.merit.meritShop.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;


    @PostMapping("/order")
    public ResponseEntity getOrderPage(@RequestBody OrderItemDto dto){

        Long orderId= orderService.order(dto);

        return new ResponseEntity<Long>(orderId, HttpStatus.OK);
    }
    @GetMapping("/pay/{orderId}")
    public String getOrderPage(@PathVariable("orderId") Long orderId, Model model){

        Orders orders = orderService.findById(orderId);

        model.addAttribute("orders",orders);


        return "pay/pay";
    }

    @GetMapping("/pay")
    public String getPayForm(OrderItemDto dto, Model model){


        System.out.println(dto);
       // model.addAttribute("orders",orders);


        return "pay/pay";
    }
}
