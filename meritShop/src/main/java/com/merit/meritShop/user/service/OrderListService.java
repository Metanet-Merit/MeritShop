package com.merit.meritShop.user.service;

import com.merit.meritShop.common.domain.Result;
import com.merit.meritShop.common.domain.ResultCode;
import com.merit.meritShop.order.domain.OrderItem;
import com.merit.meritShop.order.repository.OrderItemRepository;
import com.merit.meritShop.order.repository.OrderRepository;
import com.merit.meritShop.user.dto.OrderDTO;
import com.merit.meritShop.order.domain.Orders;
import com.merit.meritShop.user.domain.User;
import com.merit.meritShop.user.dto.OrderItemsDTO;
import com.merit.meritShop.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderListService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderItemRepository orderItemRepository;


    public Result<Map<String, Object>> getOrders(Long userId) {

        try {

            Optional<User> optionalUser = userRepository.findById(userId);
            if (optionalUser.isEmpty()) return ResultCode.USER_NOT_EXISTS.result();

            else {
                Map<String, Object> map = new HashMap<>();
                User user = optionalUser.get();
                List<Orders> orderList = orderRepository.findOrderByUser(user);
                List<OrderDTO> orderDTOList = new ArrayList<>();

                for (Orders order : orderList) {

                    OrderDTO orderDTO = OrderDTO.builder()
                            .userId(userId)
                            .orderId(order.getOrderId())
                            .totalPrice(order.getTotalPrice())
                            .orderDate(order.getOrderDate()).build();

                    orderDTOList.add(orderDTO);
                }
                map.put("orders", orderDTOList);
                return ResultCode.Success.result(map);
            }
        } catch (Exception e) {
            return ResultCode.DB_ERROR.result();
        }
    }

    /*
        public Result<Map<String, Object>> getOderItems(Long orderId) {

            try {

                Optional<Orders> optionalOrders=orderRepository.findById(orderId);
                if(optionalOrders.isEmpty()) return ResultCode.ORDER_NOT_EXISTS.result();

                else{
                    Map<String, Object> map = new HashMap<>();
                    Orders order=optionalOrders.get();
                    List<OrderItem> orderItemList=orderItemRepository.findOrderItemByOrders(order);
                    List<OrderItemsDTO> orderItemsDTOS=new ArrayList<>();

                    for (OrderItem orderItem:orderItemList) {

                        OrderItemsDTO orderItemsDTO= OrderItemsDTO.builder()
                                .count(orderItem.getCount())
                                .orderItemName(orderItem.getItem().getItemName()).build();

                        orderItemsDTOS.add(orderItemsDTO);
                    }
                    map.put("orderItems", orderItemsDTOS);
                    return ResultCode.Success.result(map);
                }
            } catch (Exception e) {
                return ResultCode.DB_ERROR.result();
            }
        }
    */
    public Result<Map<String, Object>> getOderItems(Long userId) {

        try {
            Optional<User> optionalUser = userRepository.findById(userId);

            if (optionalUser.isEmpty()) return ResultCode.USER_NOT_EXISTS.result();

            else {

                Map<String, Object> map = new HashMap<>();
                User user = optionalUser.get();
                List<Orders> orderList = orderRepository.findOrderByUser(user);
                List<OrderItemsDTO> orderItemsDTOS = new ArrayList<>();
                for (Orders order : orderList) {
                    List<OrderItem> orderItemList = orderItemRepository.findOrderItemByOrders(order);
                    for (OrderItem orderItem : orderItemList) {

                        OrderItemsDTO orderItemsDTO = OrderItemsDTO.builder()
                                .count(orderItem.getCount())
                                .orderItemName(orderItem.getItem().getItemName())
                                .reviewed(orderItem.isReviewed())
                                .orderDate(orderItem.getOrders().getOrderDate())
                                .build();

                        orderItemsDTOS.add(orderItemsDTO);
                    }

                }
                map.put("orderItems", orderItemsDTOS);
                return ResultCode.Success.result(map);
            }

        } catch (Exception e) {
            return ResultCode.DB_ERROR.result();
        }
    }

}
