package com.merit.meritShop.item.domain;
import com.merit.meritShop.common.domain.BaseEntity;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@DynamicInsert
@NoArgsConstructor
@Table(name="item_option")
public class ItemOption extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="item_option_id")
    private Long itemOptionId;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="item_id")
    private Item item;

    private int quantity;
    private String optName;
    public ItemOption(ItemOptionDto dto,Item item){
        this.item = item;
        optName = dto.getOptionName();
        quantity = dto.getQuantity();
    }
    public ItemOptionDto toItemOptionDto(){

        return  new ItemOptionDto(itemOptionId,optName,quantity);
    }

    @Override
    public String toString() {
        return "ItemOption{" +
                "optId=" + itemOptionId +
                ", item=" + item +
                ", optName='" + optName + '\'' +
                ", quantity=" + quantity +
                '}';
    }

}