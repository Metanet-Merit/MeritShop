package com.merit.meritShop.item.repository;

import com.merit.meritShop.item.domain.Item;
import com.merit.meritShop.item.domain.ItemImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemImgRepository extends JpaRepository<ItemImg,Long> {
    List<ItemImg> findAllByItem(Item item);
    void deleteAllByItemAndItemImgIdNotIn(Item item,List<Long>id);

}
