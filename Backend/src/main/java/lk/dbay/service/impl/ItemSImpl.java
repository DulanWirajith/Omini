package lk.dbay.service.impl;

import lk.dbay.dto.ItemDTO;
import lk.dbay.dto.ItemFeatureDTO;
import lk.dbay.dto.ItemImgDTO;
import lk.dbay.entity.*;
import lk.dbay.repository.ItemFeatureR;
import lk.dbay.repository.ItemImgR;
import lk.dbay.repository.ItemR;
import lk.dbay.service.ItemS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class ItemSImpl implements ItemS {

    @Autowired
    private ItemR itemR;
    @Autowired
    private ItemFeatureR itemFeatureR;
    @Autowired
    private ItemImgR itemImgR;

    @Override
    public ItemDTO addItem(Item item, MultipartFile[] files) throws Exception {
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
            Set<ItemImg> itemImgs = new HashSet<>();
            LocalDateTime localDateTime = LocalDateTime.now();
            String format = localDateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            int i = 0;
            for (MultipartFile file : files) {
                ItemImg itemImg = new ItemImg();
                itemImg.setItemImgId("ITIMG" + ++i + format);
                itemImg.setItemImg(file.getBytes());
                itemImg.setItemImgName(StringUtils.cleanPath(file.getOriginalFilename()));
                itemImg.setItemImgType(file.getContentType());
                itemImg.setItem(item);
                itemImgs.add(itemImg);
            }
            item.setItemImgs(itemImgs);
            return new ItemDTO(itemR.save(item), false);
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
            itemDTOS.add(new ItemDTO(item, false));
        }
        return itemDTOS;
    }

    @Override
    public List<ItemDTO> getItemsBusinessCategory(String businessProfileId, String businessCategoryId) {
        List<Item> itemList = itemR.getAllByBusinessProfileCategory_BusinessProfileCategoryId(new BusinessProfileCategoryPK(businessProfileId, businessCategoryId));
        List<ItemDTO> itemDTOS = new ArrayList<>();
        for (Item item : itemList) {
            ItemDTO itemDTO = new ItemDTO(item, false);
            itemDTO.setItemId(item.getItemId());
            itemDTO.setName(item.getItemTitle());
            itemDTOS.add(itemDTO);
        }
        return itemDTOS;
    }

    @Override
    public ItemImgDTO getItemImg(String id) {
        Optional<ItemImg> itemImgOptional = itemImgR.findById(id);
        if (itemImgOptional.isPresent()) {
            ItemImg itemImg = itemImgOptional.get();
            return new ItemImgDTO(itemImg);
        }
        return null;
    }

    @Override
    public List<ItemDTO> getItemsOrdered(String businessProfileId, String businessCategoryId, int start, int limit) {
        List<Item> itemList = itemR.getItemsOrdered(new BusinessProfileCategoryPK(businessProfileId, businessCategoryId), PageRequest.of(start, limit));
        List<ItemDTO> itemDTOS = new ArrayList<>();
        for (Item item : itemList) {
            itemDTOS.add(new ItemDTO(item, true));
        }
        return itemDTOS;
    }

    @Override
    public ItemDTO getItemSelected(String itemId) {
        Optional<Item> itemOptional = itemR.findById(itemId);
        if (itemOptional.isPresent()) {
            Item item = itemOptional.get();
            List<ItemFeature> itemFeatureRAll = itemFeatureR.getAllByBusinessCategory_BusinessCategoryIdAndConfirmed(item.getBusinessProfileCategory().getBusinessCategory().getBusinessCategoryId(), true);
            List<ItemFeatureDTO> itemFeatureDTOS = new ArrayList<>();
            for (ItemFeature itemFeature : itemFeatureRAll) {
                itemFeatureDTOS.add(new ItemFeatureDTO(itemFeature));
            }
            ItemDTO itemDTO = new ItemDTO(item, true, item.getBusinessProfileCategory(), item.getItemItemFeatures());
            itemDTO.setItemFeatures(itemFeatureDTOS);
            return itemDTO;
        }
        return null;
    }

    @Override
    public boolean setItemAvailable(String itemId) {
        Optional<Item> itemOptional = itemR.findById(itemId);
        if (itemOptional.isPresent()) {
            Item item = itemOptional.get();
            item.setItemAvailable(!item.isItemAvailable());
            itemR.save(item);
            return item.isItemAvailable();
        }
        return false;
    }
}
