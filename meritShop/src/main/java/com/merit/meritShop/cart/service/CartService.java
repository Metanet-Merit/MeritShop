package com.merit.meritShop.cart.service;

import com.merit.meritShop.board.domain.Qna;
import com.merit.meritShop.cart.domain.Cart;
import com.merit.meritShop.cart.dto.CartDto;
import com.merit.meritShop.cart.repository.CartRepository;
import com.merit.meritShop.item.domain.Item;
import com.merit.meritShop.item.repository.ItemRepository;
import com.merit.meritShop.user.domain.User;
import com.merit.meritShop.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    ItemRepository itemRepository;

    //목록
    public List<Cart> cartList(Long userId) {
        User user=userRepository.findById(userId).get();
        List<Cart> cartList = cartRepository.findByUser(user);

        return cartList;
    }

    //장바구니 등록
    public String add(CartDto cartDto, Long userId) {
        try {
            User user = userRepository.findById(userId).get();
            Item item = itemRepository.findById(cartDto.getItemId()).get();
            Cart cart = new Cart();
            cart.setUser(user);
            cart.setItem(item);
            cart.setCount(cartDto.getCount());
            cartRepository.save(cart);

            return "success";
        } catch(Exception e) {
            return "dbErr";
        }

    }

    //장바구니 삭제
    public String deleteCart(Long cartId) {
      try{
          Cart cart=cartRepository.findById(cartId).get();
          cartRepository.delete(cart);
          return "Success";
      }catch(Exception e){
          return "Err";
      }
    }
}
