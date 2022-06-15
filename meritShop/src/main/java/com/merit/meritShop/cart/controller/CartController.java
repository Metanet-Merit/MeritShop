package com.merit.meritShop.cart.controller;

import com.merit.meritShop.cart.domain.Cart;
import com.merit.meritShop.cart.dto.CartDto;
import com.merit.meritShop.cart.service.CartService;
import com.merit.meritShop.item.repository.ItemRepository;
import com.merit.meritShop.order.domain.OrderItemDto;
import com.merit.meritShop.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private  final UserRepository userRepository;
    private  final ItemRepository itemRepository;


    //목록
    @GetMapping("/cart")
    public String cartList(Model model, @CookieValue(name = "userId", required = false) Long userId) {
        List<Cart> cartList = cartService.cartList(userId);
        System.out.println("fuck");
        for(Cart c : cartList){
            System.out.println(c);
        }
        model.addAttribute("list", cartList);
        return "cart/cart";
    }
    //장바구니 등록

    @PostMapping("/cart")
    public ResponseEntity add(@RequestBody CartDto cartDto, @CookieValue("userId") Long userId) {
        // dto : itemId,itemOptionId ,count
        System.out.println(cartDto);
        cartDto.setUserId(userId);
        String result = cartService.add(cartDto, userId);
        Long itemId = cartDto.getItemId();
//        if(result == "success") {
//            return "cart/cart";
//        }
//        else {
//            return "redirect:/item/{itemId}";
//        }

        return new ResponseEntity<Long>(2L, HttpStatus.OK);
    }
    //장바구니 삭제

    @DeleteMapping("/cart/delete/{cartId}")
    public ResponseEntity deleteCart(@PathVariable Long cartId) {
        String result=cartService.deleteCart(cartId);
        //if(result!="Success") model.addAttribute("errMsg","상품 삭제를 실패했습니다");

        return new ResponseEntity<Long>(cartId, HttpStatus.OK);
    }
}
