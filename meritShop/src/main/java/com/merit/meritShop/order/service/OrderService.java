package com.merit.meritShop.order.service;

import com.merit.meritShop.item.domain.Item;
import com.merit.meritShop.item.domain.ItemOption;
import com.merit.meritShop.item.domain.ItemSellStatus;
import com.merit.meritShop.item.repository.ItemOptionRepository;
import com.merit.meritShop.item.repository.ItemRepository;
import com.merit.meritShop.item.service.ItemOptionService;
import com.merit.meritShop.order.domain.OrderItem;
import com.merit.meritShop.order.domain.OrderItemDto;
import com.merit.meritShop.order.domain.Orders;
import com.merit.meritShop.order.domain.PayFormDto;
import com.merit.meritShop.order.repository.OrderItemRepository;
import com.merit.meritShop.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ItemRepository itemRepository;
    private final ItemOptionRepository itemOptionRepository;
    private final ItemOptionService itemOptionService;

    public long getOrderId(){
        return orderRepository.countAllBy()+1;
    }
    public Orders findById(Long id){
        return orderRepository.findById(id).get();
    }


    @Transactional
    public Long order(PayFormDto formDto){
        List<OrderItem> list = new ArrayList<>();
        Orders orders = new Orders();
        orders.setOrderId(formDto.getOrderId());

        for(OrderItemDto dto:formDto.getOrderItemDtoList()) {

            Item item = itemRepository.findById(dto.getItemId()).get();
            ItemOption option = itemOptionRepository.findById(dto.getItemOptionId()).get();
            int remain = option.getQuantity() - dto.getCount();
            if (remain < 0) {
                throw new RuntimeException("재고부족");
            }else if(remain==0){
                int totalStocks = itemOptionService.getItemStocks(item);
                if(totalStocks == dto.getCount()){
                    item.setStatus(ItemSellStatus.SOLD_OUT);
                }else{
                item.setStatus(ItemSellStatus.LACK);}
            }
            itemOptionRepository.updateOptionQuantity(option.getItemOptionId(), remain);


            OrderItem orderItem = new OrderItem();
            orderItem.setItem(item);
            orderItem.setCount(dto.getCount());
            orderItem.setReviewed(false);
            orderItem.setOrderItemPrice(item.getPrice() * dto.getCount());
            orderItem.setOrders(orders);
            orderItem = orderItemRepository.save(orderItem);
            list.add(orderItemRepository.save(orderItem));

        }
        orders.setOrderItemList(list);
        orders.updateTotalPrice();
        orders.setOrderDate(LocalDateTime.now());
        orders =orderRepository.save(orders);



        return orders.getOrderId();

    }

    public void orderCancle(){
        //아직 구현 안했음
    }
}
