package com.merit.meritShop.item.controller;

import com.merit.meritShop.board.service.QnaService;
import com.merit.meritShop.common.domain.Result;
import com.merit.meritShop.item.domain.*;
import com.merit.meritShop.item.repository.ItemImgRepository;
import com.merit.meritShop.item.repository.ItemOptionRepository;
import com.merit.meritShop.item.repository.ItemRepository;
import com.merit.meritShop.item.service.ItemService;
import com.merit.meritShop.user.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    @Value("${uploadPath}")
    private String path;

    private final ItemService itemService;
    private final ItemImgRepository itemImgRepository;
    private final ItemOptionRepository itemOptionRepository;
    private final ReviewService reviewService;
    private final QnaService qnaService;
    private final ItemRepository itemRepository;

    @GetMapping("item/{id}")
    public String getItemDetail(@PathVariable("id") long id, Model model){
        Item item = itemService.getItemByItemId(id);
        ItemFormDto itemFormDto = item.toItemFormDto();
        List<ItemOptionDto> itemOptionDtoList = new ArrayList<>();
        List<ItemImgDto> itemImgDto = new ArrayList<>();
        for( ItemImg img:itemImgRepository.findAllByItem(item)){
            itemImgDto.add(img.toItemImgDto());
        }
        for(ItemOption option:itemOptionRepository.findAllByItem(item)){

            itemOptionDtoList.add(option.toItemOptionDto());
        }
        itemFormDto.setOptions(itemOptionDtoList);

        Result result=reviewService.getItemReviews(id);
        Result qnaResult=qnaService.getItemQnas(id);

        model.addAttribute("qnaResult",qnaResult);
        model.addAttribute("result", result);
        model.addAttribute("itemFormDto",itemFormDto);
        model.addAttribute("itemImgDto",itemImgDto);
        model.addAttribute("category",item.getCategory());

        return "item/itemDetail";
    }

    @GetMapping("/admin/itemForm")
    public String getItemForm(){
        return "item/itemForm";
    }

    @PostMapping("/admin/newItem")
    public String createItem(ItemFormDto dto, String[] optionName,String[] quantity , MultipartFile[] fileUpload) throws IOException {

        List<ItemOptionDto> list = new ArrayList<>();
        for(int i=0; i<optionName.length;i++){
            optionName[i] =optionName[i].replace("<","&lt;");
            optionName[i] =optionName[i].replace(">","&gt;");
            optionName[i] =optionName[i].replace("&","%amp;");
            optionName[i] =optionName[i].replace("'","&#x27;");
            optionName[i] =optionName[i].replace("/","&#x2F;");
            ItemOptionDto itemOptionDto = new ItemOptionDto();
            itemOptionDto.setOptionName(optionName[i]);
            itemOptionDto.setQuantity(Integer.parseInt(quantity[i]));

            list.add(itemOptionDto);
        }
        dto.setOptions(list);
        itemService.save(dto,fileUpload);


        return "item/itemForm";
    }

    @GetMapping("/admin/updateItem/{id}")
    public String edit(@PathVariable("id") long id, Model model){
        Item item = itemService.getItemByItemId(id);
        ItemFormDto itemFormDto = item.toItemFormDto();
        List<ItemOptionDto> itemOptionDtoList = new ArrayList<>();
        List<ItemImgDto> itemImgDto = new ArrayList<>();
        for( ItemImg img:itemImgRepository.findAllByItem(item)){
            itemImgDto.add(img.toItemImgDto());
        }
        for(ItemOption option:itemOptionRepository.findAllByItem(item)){

            itemOptionDtoList.add(option.toItemOptionDto());
        }
        itemFormDto.setOptions(itemOptionDtoList);


        model.addAttribute("itemFormDto",itemFormDto);
        model.addAttribute("itemImgDto",itemImgDto);

        return "item/itemUpdateForm";
    }

    @PostMapping("/admin/updateItem")
    public String updateItem(ItemFormDto dto, String[] itemOptionId,String[] optionName,String[] quantity , String[] itemImgId,MultipartFile[] fileUpload) throws IOException {


        List<ItemOptionDto> list = new ArrayList<>();

        for(int i=0; i<itemOptionId.length; i++){
            optionName[i] =optionName[i].replace("<","&lt;");
            optionName[i] =optionName[i].replace(">","&gt;");
            optionName[i] =optionName[i].replace("&","%amp;");
            optionName[i] =optionName[i].replace("'","&#x27;");
            optionName[i] =optionName[i].replace("/","&#x2F;");

            itemOptionRepository.updateOptionNameQuantity(optionName[i],Long.parseLong(itemOptionId[i]),Integer.parseInt(quantity[i]));
        }

        for(int i=itemOptionId.length; i<optionName.length;i++){
            optionName[i] =optionName[i].replace("<","&lt;");
            optionName[i] =optionName[i].replace(">","&gt;");
            optionName[i] =optionName[i].replace("&","%amp;");
            optionName[i] =optionName[i].replace("'","&#x27;");
            optionName[i] =optionName[i].replace("/","&#x2F;");

            ItemOptionDto itemOptionDto = new ItemOptionDto();
            itemOptionDto.setOptionName(optionName[i]);
            itemOptionDto.setQuantity(Integer.parseInt(quantity[i]));

            list.add(itemOptionDto);
        }

        List<Long> itemImgIdList = new ArrayList<>();
        List<Long> optIdList =  new ArrayList<>();
        for(String id : itemImgId){
            itemImgIdList.add(Long.parseLong(id));
        }
        for(String id:itemOptionId){
            optIdList.add(Long.parseLong(id));
        }

        dto.setOptions(list);
        itemService.updateItem(dto,fileUpload,itemImgIdList,optIdList);


        return "redirect:/admin";
    }

    @GetMapping("/admin/itemList")
    String getAdminItemList(Model model,@PageableDefault Pageable pageable){

      //  PageRequest pageRequest =
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        pageable = PageRequest.of(page, 5);
        Page<Item> itemList = itemService.findAllItem(pageable);
        model.addAttribute("items",itemList);
       // model.addAttribute("maxPage",5);
      //  model.addAttribute("totalCount",itemList.getTotalElements());
        return "admin/item/itemList";
    }

    @GetMapping("/admin/item/search")
    public String search(String keyword, Model model, @PageableDefault(direction = Sort.Direction.DESC) Pageable pageable) {
     //   PageRequest pageRequest =  PageRequest.of(0,8, Sort.by(Sort.Direction.DESC,"createdDate"));
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        pageable = PageRequest.of(page, 5);
        Page<Item> itemList = itemRepository.findByItemNameContaining(keyword, pageable);
        model.addAttribute("items",itemList);
      //  model.addAttribute("maxPage",5);
      //  model.addAttribute("totalCount",itemList.getTotalElements());
        return "admin/item/itemList";
    }


}
