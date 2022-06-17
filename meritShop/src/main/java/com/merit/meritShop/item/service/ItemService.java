package com.merit.meritShop.item.service;

import com.merit.meritShop.item.domain.*;
import com.merit.meritShop.item.repository.ItemImgRepository;
import com.merit.meritShop.item.repository.ItemOptionRepository;
import com.merit.meritShop.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final ItemImgRepository itemImgRepository;
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
        List<ItemImg> imgList = new ArrayList<>();
        for(int i=0;i<files.length;i++){
            ItemImg img = new ItemImg();
            img.setItem(item);
            img =itemImgService.saveItemImg(img,files[i]);
            imgList.add(img);
            url=img.getImgUrl();
            if(i ==0){
                item.setImgUrl(url);
            }
        }
        item.setImgList(imgList);

    }
    @Transactional
    public void updateItem(ItemFormDto dto, MultipartFile[] files,List<Long> itemImgIds,List<Long> optIds) throws IOException {

        Item item = itemRepository.findById(dto.getItemId()).get();
        item.of(dto);
        String imgUrl;
        itemOptionRepository.deleteAllByItemAndItemOptionIdNotIn(item,optIds);
        List<ItemOption> itemOptionList =new ArrayList<>();
        List<ItemImg> itemImgList = itemImgRepository.findAllByItemAndItemImgIdIn(item,itemImgIds);
        for(ItemOptionDto d: dto.getOptions()){

            ItemOption io=itemOptionRepository.save(new ItemOption(d,item));
            itemOptionList.add(io);
        }
        item.setOpt(itemOptionList);

        itemImgService.deleteItemImg(item,itemImgIds);

        if(files!=null){
        for(int i=0;i<files.length;i++){
            ItemImg img = new ItemImg();
            img.setItem(item);
            img = itemImgService.saveItemImg(img,files[i]);
            itemImgList.add(img);
        }
        }
        item.setImgList(itemImgList);
        imgUrl = itemImgList.get(0).getImgUrl();
        System.out.println(imgUrl);
        item.setImgUrl(imgUrl);
        for(ItemImg img:item.getImgList()){
            System.out.println(img.getImgUrl());
        }
    }


    public Item getItemByItemId(long id){

        return itemRepository.findById(id).orElseThrow(()->new NoSuchElementException());
    }

    public Page<Item> findAllItem(Pageable pageable){
        return itemRepository.findAll(pageable);
    }
}
