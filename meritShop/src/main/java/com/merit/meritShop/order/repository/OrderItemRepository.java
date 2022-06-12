package com.merit.meritShop.order.repository;

import com.merit.meritShop.order.domain.OrderItem;
import com.merit.meritShop.order.domain.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {
    List<OrderItem> findOrderItemByOrders(Orders order);

}
