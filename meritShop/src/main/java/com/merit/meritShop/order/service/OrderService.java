package com.merit.meritShop.order.service;

import com.merit.meritShop.coupon.domain.Coupon;
import com.merit.meritShop.coupon.domain.CouponCase;
import com.merit.meritShop.coupon.repository.CouponCaseRepository;
import com.merit.meritShop.coupon.repository.CouponRepository;
import com.merit.meritShop.item.domain.Item;
import com.merit.meritShop.item.domain.ItemOption;
import com.merit.meritShop.item.domain.ItemSellStatus;
import com.merit.meritShop.item.repository.ItemOptionRepository;
import com.merit.meritShop.item.repository.ItemRepository;
import com.merit.meritShop.item.service.ItemOptionService;
import com.merit.meritShop.order.domain.*;
import com.merit.meritShop.order.repository.OrderItemRepository;
import com.merit.meritShop.order.repository.OrderRepository;
import com.merit.meritShop.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ItemRepository itemRepository;
    private final ItemOptionRepository itemOptionRepository;
    private final ItemOptionService itemOptionService;
    private final CouponCaseRepository couponCaseRepository;
    private final CouponRepository couponRepository;


    public Orders findById(Long id){
        return orderRepository.findById(id).get();
    }


    public List<OrderItemHistDto> getOrderHistory(User user){

        List<OrderItemHistDto> dtoList = new ArrayList<>();

        List<Orders> orders = orderRepository.findOrderByUser(user);

        if(!orders.isEmpty()) {

            for (Orders order : orders) {
                List<OrderItem> items = orderItemRepository.findOrderItemByOrders(order);
                for (OrderItem item : items) {
                    dtoList.add(item.toHistDto());
                }
            }
        }

        return dtoList;
    }

    @Transactional
    public Long order(User user,PayFormDto formDto){
        List<OrderItem> list = new ArrayList<>();
        Orders orders = new Orders();
        orders.setTransactionCode(formDto.getTransactionCode());
        Long code = 0L;
        //System.out.println(formDto.getCouponCaseId());
        int discountP =0;
        if(formDto.getCouponCaseId()!=null) {
            CouponCase couponCase = couponCaseRepository.findById(formDto.getCouponCaseId()).get();
            Coupon coupon = couponRepository.findById(couponCase.getCoupon().getCouponId()).get();

            couponCase.setUsed(true);
            discountP=coupon.getDiscountPrice();

        }
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
            orderItem.setItemOptionId(dto.getItemOptionId());
            orderItem = orderItemRepository.save(orderItem);
            list.add(orderItemRepository.save(orderItem));

        }

        orders.setOrderItemList(list);
        orders.setAddress(formDto.getAddress());
        orders.setRecipient(formDto.getRecipient());
        orders.updateTotalPrice();

        orders.setTotalPrice(orders.getTotalPrice()-discountP);

        orders.setOrderDate(LocalDateTime.now());
        if(user!=null){
            orders.setUser(user);

        }
        orders.setCouponCaseId(formDto.getCouponCaseId());
        orders =orderRepository.save(orders);
        log.info("[order] "+orders.getTotalPrice());


        return code=200L;

    }

    @Transactional
    public void orderCancel(Long userId,Long orderId){
        //아직 구현 안했음
        Orders order  =  orderRepository.findById(orderId).get();

        for(OrderItem item: order.getOrderItemList()){

            Long itemOptionId = item.getItemOptionId();
            int count = item.getCount();
            ItemOption option = itemOptionRepository.findById(itemOptionId).get();
            option.setQuantity(option.getQuantity()+count);

        }
        if(order.getCouponCaseId()!=null) {
            CouponCase coupon = couponCaseRepository.findById(order.getCouponCaseId()).get();
            coupon.setUsed(false);
            order.setTotalPrice(order.getTotalPrice() + coupon.getCoupon().getDiscountPrice());
        }
        /*환불로직*/
        orderRepository.delete(order);

    }
}
