package com.merit.meritShop.item.service;

import com.merit.meritShop.item.domain.*;
import com.merit.meritShop.item.repository.ItemOptionRepository;
import com.merit.meritShop.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final ItemOptionRepository itemOptionRepository;
    private final ItemImgService itemImgService;


    @Transactional
    public void save(ItemFormDto dto, MultipartFile[] files) throws IOException {
        Item item = itemRepository.save(new Item().of(dto));
        List<ItemOption> itemOptionList =new ArrayList<>();
        for(ItemOptionDto d: dto.getOptions()){

            ItemOption io=itemOptionRepository.save(new ItemOption(d,item));
            itemOptionList.add(io);
        }
        item.setOpt(itemOptionList);
        String url;
        for(int i=0;i<files.length;i++){
            ItemImg img = new ItemImg();
            img.setItem(item);
            url =itemImgService.saveItemImg(img,files[i]);
            if(i ==0){
                item.setImgUrl(url);
            }
        }

    }

    public void updateItem(ItemFormDto dto, MultipartFile[] files,List<Long> itemImgIds,List<Long> optIds) throws IOException {

        Item item = itemRepository.findById(dto.getItemId()).get();
        item.of(dto);
        itemOptionRepository.deleteAllByItemAndItemOptionIdNotIn(item,optIds);
        List<ItemOption> itemOptionList =new ArrayList<>();
        for(ItemOptionDto d: dto.getOptions()){

            ItemOption io=itemOptionRepository.save(new ItemOption(d,item));
            itemOptionList.add(io);
        }
        item.setOpt(itemOptionList);

        itemImgService.deleteItemImg(item,itemImgIds);
        for(int i=0;i<files.length;i++){
            ItemImg img = new ItemImg();
            img.setItem(item);
            itemImgService.saveItemImg(img,files[i]);
        }
    }


    public Item getItemByItemId(long id){

        return itemRepository.findById(id).orElseThrow(()->new NoSuchElementException());
    }
}
