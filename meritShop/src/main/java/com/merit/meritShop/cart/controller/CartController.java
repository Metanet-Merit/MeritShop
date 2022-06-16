package com.merit.meritShop.cart.controller;

import com.merit.meritShop.cart.domain.Cart;
import com.merit.meritShop.cart.dto.CartDto;
import com.merit.meritShop.cart.dto.CartViewDto;
import com.merit.meritShop.cart.service.CartService;
import com.merit.meritShop.item.domain.Item;
import com.merit.meritShop.item.domain.ItemOption;
import com.merit.meritShop.item.repository.ItemOptionRepository;
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

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private  final UserRepository userRepository;
    private  final ItemRepository itemRepository;
    private final ItemOptionRepository itemOptionRepository;


    //목록
    @GetMapping("/cart")
    public String cartList(Model model, @CookieValue(name = "userId", required = false) Long userId) {
        List<Cart> cartList = cartService.cartList(userId);
        List<CartViewDto> cartViewList = new ArrayList<>();
        int totalPrice = 0;
        for(Cart c : cartList){
            ItemOption itemOption = itemOptionRepository.findById(c.getItemOptionId()).get();
            CartViewDto dto = CartViewDto.builder()
                    .itemOptionId(c.getItemOptionId())
                    .itemId(c.getItem().getItemId())
                    .count(c.getCount())
                    .cartId(c.getCartId())
                    .imgUrl(c.getItem().getImgUrl())
                    .itemName(c.getItem().getItemName())
                    .price(c.getItem().getPrice() * c.getCount())
                    .optionName(itemOption.getOptName())
                    .optionList(c.getItem().getOpt())
                    .build();
            cartViewList.add(dto);
            totalPrice += dto.getPrice();
        }
        model.addAttribute("list", cartViewList);
        model.addAttribute("total", totalPrice);
        model.addAttribute("shipping", 3000);
        //model.addAttribute("list", cartList);
        return "cart/cart";
    }
    //장바구니 등록

    @PostMapping("/cart")
    public ResponseEntity add(@RequestBody CartDto cartDto, @CookieValue("userId") Long userId) {
        // dto : itemId,itemOptionId ,count
        cartDto.setUserId(userId);
        if (cartDto.getItemOptionId() == null) {
            System.out.println(cartDto.toString());
            Item item = itemRepository.findById(cartDto.getItemId()).get();
            cartDto.setItemOptionId(item.getOpt().get(0).getItemOptionId());
        }
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

    //장바구니 아이템 수량 증가

    @PutMapping("/cart/plus/{cartId}")
    public ResponseEntity plusCount(@PathVariable Long cartId) {
        System.out.println("과연");
        String result=cartService.plusCartCount(cartId);
        if(result!="Success") System.out.println("증가 실패");
        return new ResponseEntity<Long>(cartId, HttpStatus.OK);
    }
    //장바구니 아이템 수량 감소
    @PutMapping("/cart/minus/{cartId}")
    public ResponseEntity minusCount(@PathVariable Long cartId) {
        String result=cartService.minusCartCount(cartId);
        if(result!="Success") System.out.println("감소 실패");
        return new ResponseEntity<Long>(cartId, HttpStatus.OK);
    }
}
