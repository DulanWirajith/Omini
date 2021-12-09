package lk.dbay.service.impl;

import lk.dbay.dto.ItemDTO;
import lk.dbay.entity.*;
import lk.dbay.repository.ItemFeatureR;
import lk.dbay.repository.ItemR;
import lk.dbay.service.ItemS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class ItemSImpl implements ItemS {

    @Autowired
    private ItemR itemR;
    @Autowired
    private ItemFeatureR itemFeatureR;

    @Override
    public ItemDTO addItem(Item item, MultipartFile file) throws Exception {
        try {
            BusinessProfileCategory businessProfileCategory = item.getBusinessProfileCategory();
            item.setItemId("ITM" + item.getItemTitle() + businessProfileCategory.getBusinessProfile().getBusinessProId() + businessProfileCategory.getBusinessCategory().getBusinessCategoryId());
            for (ItemItemFeature itemItemFeature : item.getItemItemFeatures()) {
                if (itemItemFeature.getItemFeature().getItemFeatureId().equals("0")) {
                    LocalDateTime localDateTime = LocalDateTime.now();
                    String format = localDateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
                    itemItemFeature.getItemFeature().setItemFeatureId("ITF" + format);
                    ItemFeature itemFeature = itemFeatureR.save(itemItemFeature.getItemFeature());
                    itemItemFeature.setItemFeature(itemFeature);
                }
                itemItemFeature.setItemItemFeatureId(new ItemItemFeaturePK(item.getItemId(), itemItemFeature.getItemFeature().getItemFeatureId()));
                itemItemFeature.setItem(item);
            }
            businessProfileCategory.setBusinessProfileCategoryId(
                    new BusinessProfileCategoryPK(businessProfileCategory.getBusinessProfile().getBusinessProId(), businessProfileCategory.getBusinessCategory().getBusinessCategoryId())
            );
            item.setItemImg(file.getBytes());
            item.setItemImgName(StringUtils.cleanPath(file.getOriginalFilename()));
            item.setItemImgType(file.getContentType());
            return new ItemDTO(itemR.save(item));
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Something went wrong");
        }
    }

    @Override
    public List<ItemDTO> getItems() {
        List<Item> itemList = itemR.findAll();
        List<ItemDTO> itemDTOS = new ArrayList<>();
        for (Item item : itemList) {
            itemDTOS.add(new ItemDTO(item));
        }
        return itemDTOS;
    }

    @Override
    public List<ItemDTO> getItemsBusinessCategory(String businessProfileId, String businessCategoryId) {
        List<Item> itemList = itemR.getAllByBusinessProfileCategory_BusinessProfileCategoryId(new BusinessProfileCategoryPK(businessProfileId, businessCategoryId));
        List<ItemDTO> itemDTOS = new ArrayList<>();
        for (Item item : itemList) {
            ItemDTO itemDTO = new ItemDTO(item);
            itemDTO.setItemId(item.getItemId());
            itemDTO.setName(item.getItemTitle());
            itemDTOS.add(itemDTO);
        }
        return itemDTOS;
    }
}
