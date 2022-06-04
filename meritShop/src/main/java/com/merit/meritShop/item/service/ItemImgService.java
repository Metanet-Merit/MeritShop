package com.merit.meritShop.item.service;


import com.merit.meritShop.item.domain.Item;
import com.merit.meritShop.item.domain.ItemImg;
import com.merit.meritShop.item.repository.ItemImgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemImgService {
    @Value("${itemImgLocation}")
    private String itemImgLocation;
    private  final ItemImgRepository itemImgRepository;
    private  final FileService fileService;

    public String saveItemImg(ItemImg itemImg, MultipartFile itemImgFile) throws IOException {
        String originName = itemImgFile.getOriginalFilename();
        String imgName ="";
        String imgUrl = "";


        if(!StringUtils.isEmpty(originName)){
            imgName = fileService.upload(itemImgLocation,originName,itemImgFile.getBytes());
            imgUrl = "/images/item/"+imgName;
        }
        itemImg.updateItemImg(originName,imgName,imgUrl);
        itemImgRepository.save(itemImg);
        return imgUrl;
    }

    public void updateItemImg(Long itemImgId,MultipartFile itemImgFile) throws IOException {
        if(!itemImgFile.isEmpty()){
            ItemImg savedItemImg = itemImgRepository.findById(itemImgId).orElseThrow(EntityNotFoundException::new);

            if(!StringUtils.isEmpty(savedItemImg.getImgName())){
                fileService.deleteFile(itemImgLocation+"/"+savedItemImg.getImgName());
            }

            String originFileName =itemImgFile.getOriginalFilename();
            String imgName = fileService.upload(itemImgLocation,originFileName,itemImgFile.getBytes());
            String imgUrl = "/images/item/"+imgName;
            savedItemImg.updateItemImg(originFileName,imgName,imgUrl);
        }
    }

    @Transactional
    public void deleteItemImg(Item item , List<Long> id){
        itemImgRepository.deleteAllByItemAndItemImgIdNotIn(item,id);
    }
}