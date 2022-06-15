package com.merit.meritShop.item.repository;

import com.merit.meritShop.item.domain.Item;
import com.merit.meritShop.item.domain.ItemOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface ItemOptionRepository extends JpaRepository<ItemOption,Long> {
    List<ItemOption> findAllByItem(Item item);
    @Transactional
    void deleteAllByItemAndItemOptionIdNotIn(Item item,List<Long>optId);

    @Transactional
    @Modifying
    @Query(value="update ItemOption op set op.optName = :optName,op.quantity = :count where op.itemOptionId = :OptionId")
    void updateOptionNameQuantity(@Param("optName")String optName,@Param("OptionId") Long OptionId,@Param("count") int count);

    @Transactional
    @Modifying
    @Query(value="update ItemOption op set op.quantity = :count where op.itemOptionId = :OptionId")
    void updateOptionQuantity(@Param("OptionId") Long OptionId,@Param("count") int count);

    Optional<ItemOption> findItemOptionByItem(Item item);

}
