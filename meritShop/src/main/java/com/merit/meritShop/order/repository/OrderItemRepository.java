package com.merit.meritShop.order.repository;

import com.merit.meritShop.item.domain.Item;
import com.merit.meritShop.order.domain.OrderItem;
import com.merit.meritShop.order.domain.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findOrderItemByOrders(Orders order);
    List<OrderItem> findOrderItemByItem(Item item);


    @Query(value = "select oi " +
            "from OrderItem oi, Orders o " +
            "where o.user.userId = ?1 and o.orderId = oi.orders.orderId " +
            "order by o.orderDate desc ")
    List<OrderItem> findOrderItemsByUserId(Long userId);

}
