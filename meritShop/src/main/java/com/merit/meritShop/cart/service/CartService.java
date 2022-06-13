package com.merit.meritShop.cart.service;

import com.merit.meritShop.cart.domain.Cart;
import com.merit.meritShop.cart.repository.CartRepository;
import com.merit.meritShop.user.domain.User;
import com.merit.meritShop.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    CartRepository cartRepository;

    //목록
    public List<Cart> cartList(Long userId) {
        User user=userRepository.findById(userId).get();
        List<Cart> cartList=cartRepository.findByUser(user);
        return cartList;
    }

    //장바구니 등록
    public void add(Cart cart) {
        cartRepository.save(cart);
    }
}
