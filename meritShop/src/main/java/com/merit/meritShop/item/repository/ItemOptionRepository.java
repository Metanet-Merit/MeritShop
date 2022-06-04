package com.merit.meritShop.item.repository;

import com.merit.meritShop.item.domain.Item;
import com.merit.meritShop.item.domain.ItemOption;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface ItemOptionRepository extends JpaRepository<ItemOption,Long> {
    List<ItemOption> findAllByItem(Item item);
    @Transactional
    void deleteAllByItemAndItemOptionIdNotIn(Item item,List<Long>optId);
}
