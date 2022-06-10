package com.merit.meritShop.order.repository;

import com.merit.meritShop.order.domain.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Orders,Long> {

     Long countAllBy();
}
