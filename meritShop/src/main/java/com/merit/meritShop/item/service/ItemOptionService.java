package com.merit.meritShop.item.service;

import com.merit.meritShop.item.domain.Item;
import com.merit.meritShop.item.domain.ItemOption;
import com.merit.meritShop.item.repository.ItemOptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemOptionService {

    private final ItemOptionRepository itemOptionRepository;

    public int getItemStocks(Item item){
        List<ItemOption> list = itemOptionRepository.findAllByItem(item);

        int totalStock =0;
        for (ItemOption op:list) {
            totalStock+=op.getQuantity();
        }

        return totalStock;
    }
}
