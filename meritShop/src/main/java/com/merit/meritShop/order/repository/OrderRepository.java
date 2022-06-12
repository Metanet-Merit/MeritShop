package com.merit.meritShop.order.repository;

import com.merit.meritShop.order.domain.Orders;
import com.merit.meritShop.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders,Long> {
   List<Orders> findOrderByUser (User user);
}
