package com.merit.meritShop.order.controller;

import com.merit.meritShop.item.domain.Item;
import com.merit.meritShop.item.domain.ItemOption;
import com.merit.meritShop.item.repository.ItemOptionRepository;
import com.merit.meritShop.item.repository.ItemRepository;
import com.merit.meritShop.order.domain.OrderItemDto;
import com.merit.meritShop.order.domain.Orders;
import com.merit.meritShop.order.domain.PayFormDto;
import com.merit.meritShop.order.domain.PayItemDto;
import com.merit.meritShop.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final ItemOptionRepository itemOptionRepository;
    private final ItemRepository itemRepository;


    @PostMapping("/order")
    public ResponseEntity getOrderPage( @RequestBody PayFormDto dto ){

           // System.out.println(dto);

            orderService.order(dto);


        return new ResponseEntity<Long>(dto.getOrderId(), HttpStatus.OK);
    }


    @GetMapping("/pay")
    public String getPayForm( PayFormDto dtoList, Model model){

        List<OrderItemDto> dtos = dtoList.getOrderItemDtoList();
        List<PayItemDto> payList = new ArrayList<>();
        int totalPrice = 0;
        for(OrderItemDto dto:dtos) {
            Item item = itemRepository.findById(dto.getItemId()).get();
            ItemOption option = itemOptionRepository.findById(dto.getItemOptionId()).get();
            int remain = option.getQuantity() - dto.getCount();
            if (remain < 0) {
                throw new RuntimeException("재고부족");
            }


            payList.add(new PayItemDto(item.getItemId(),option.getItemOptionId(),item.getItemName(), dto.getCount(), item.getPrice()));


        }
        for (PayItemDto p : payList) {
            totalPrice += (p.getPrice() * p.getCount());
        }
        model.addAttribute("orderId",orderService.getOrderId());
        model.addAttribute("payList",payList);
        model.addAttribute("totalPrice",totalPrice);


        return "pay/pay";
    }
}
