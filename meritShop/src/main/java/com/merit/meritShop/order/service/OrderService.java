package com.merit.meritShop.order.service;

import com.merit.meritShop.item.domain.Item;
import com.merit.meritShop.item.domain.ItemOption;
import com.merit.meritShop.item.repository.ItemOptionRepository;
import com.merit.meritShop.item.repository.ItemRepository;
import com.merit.meritShop.order.domain.OrderItem;
import com.merit.meritShop.order.domain.OrderItemDto;
import com.merit.meritShop.order.domain.Orders;
import com.merit.meritShop.order.repository.OrderItemRepository;
import com.merit.meritShop.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ItemRepository itemRepository;
    private final ItemOptionRepository itemOptionRepository;

    public long getOrderId(){
        return orderRepository.countAllBy()+1;
    }
    public Orders findById(Long id){
        return orderRepository.findById(id).get();
    }

    public Long order(OrderItemDto dto){
        List<OrderItem> list = new ArrayList<>();

        Item item = itemRepository.findById(dto.getItemId()).get();
        ItemOption option = itemOptionRepository.findById(dto.getItemOptionId()).get();
        int remain =option.getQuantity()-dto.getCount();
        if(remain<0){
            throw new RuntimeException("재고부족");
        }
        itemOptionRepository.updateOptionQuantity(option.getItemOptionId(),remain);
        Orders orders = new Orders();

        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setCount(dto.getCount());
        orderItem.setReviewed(false);
        orderItem.setOrderItemPrice(item.getPrice()*dto.getCount());

        list.add(orderItemRepository.save(orderItem));

        orders.setOrderItemList(list);
        orders.updateTotalPrice();
        orders =orderRepository.save(orders);
        orderItem.setOrders(orders);
        return orders.getOrderId();

    }

    public void orderCancle(){

    }
}
