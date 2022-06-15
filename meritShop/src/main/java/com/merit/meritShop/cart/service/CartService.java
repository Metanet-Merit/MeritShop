package com.merit.meritShop.cart.service;

import com.merit.meritShop.board.domain.Qna;
import com.merit.meritShop.cart.domain.Cart;
import com.merit.meritShop.cart.dto.CartDto;
import com.merit.meritShop.cart.repository.CartRepository;
import com.merit.meritShop.item.domain.Item;
import com.merit.meritShop.item.domain.ItemOption;
import com.merit.meritShop.item.repository.ItemOptionRepository;
import com.merit.meritShop.item.repository.ItemRepository;
import com.merit.meritShop.user.domain.User;
import com.merit.meritShop.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Autowired
    ItemOptionRepository itemOptionRepository;

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
            cart.setItemOptionId(cartDto.getItemOptionId());
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

    //장바구니 아이템 수량 증가
    @Transactional(rollbackFor = Exception.class)
    public String plusCartCount(Long cartId) {
        Cart cart=cartRepository.findById(cartId).orElseThrow(() -> new IllegalArgumentException("해당 장바구니 없음"));
        ItemOption itemOption = itemOptionRepository.findById(cart.getItemOptionId()).orElseThrow(() -> new IllegalArgumentException("해당 아이템 옵션 없음"));
        int count = cart.getCount();
        if (count < itemOption.getQuantity()) { // 해당 상품 수량보다 크게 담아선 안된다.
            cart.setCount(cart.getCount()+1);
            return "Success";
        }
        else {
            return "Err";
        }
    }

    //장바구니 아이템 수량 감소
    @Transactional(rollbackFor = Exception.class)
    public String minusCartCount(Long cartId) {
        Cart cart=cartRepository.findById(cartId).orElseThrow(() -> new IllegalArgumentException("해당 장바구니 없음"));
        int count = cart.getCount();
        if (count != 1) { // 수량이 0이 되면 안됨
            cart.setCount(cart.getCount()-1);
            return "Success";
        }
        else {
            return "Err";
        }
    }

}
