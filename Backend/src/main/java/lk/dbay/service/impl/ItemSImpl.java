package lk.dbay.service.impl;

import lk.dbay.dto.ItemDTO;
import lk.dbay.dto.ItemFeatureDTO;
import lk.dbay.dto.ItemImgDTO;
import lk.dbay.entity.*;
import lk.dbay.repository.ItemFeatureR;
import lk.dbay.repository.ItemImgR;
import lk.dbay.repository.ItemItemFeatureR;
import lk.dbay.repository.ItemR;
import lk.dbay.service.ItemS;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
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
    @Autowired
    private ItemItemFeatureR itemItemFeatureR;

    @Override
    @Transactional
    public ItemDTO addItem(Item item, MultipartFile[] files) throws Exception {
        try {
            LocalDateTime localDateTime = LocalDateTime.now();
            String format = localDateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            BusinessProfileCategory businessProfileCategory = item.getBusinessProfileCategory();
            item.setItemId("ITM" + businessProfileCategory.getBusinessProfile().getBusinessProId() + format);
            addFeaturesToItem(item);
            addImagesToItem(item, files);
            businessProfileCategory.setBusinessProfileCategoryId(
                    new BusinessProfileCategoryPK(businessProfileCategory.getBusinessProfile().getBusinessProId(), businessProfileCategory.getBusinessCategory().getBusinessCategoryId())
            );
            return new ItemDTO(itemR.save(item), false);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Something went wrong");
        }
    }

    @Override
    @Transactional
    public ItemDTO updateItem(Item item, MultipartFile[] files, String itemId) throws Exception {
        try {
            Optional<Item> itemOptional = itemR.findById(itemId);
            if (itemOptional.isPresent()) {
                Item itemObj = itemOptional.get();
                itemObj.setItemTitle(item.getItemTitle());
                itemObj.setItemQty(item.getItemQty());
                itemObj.setItemPrice(item.getItemPrice());
                itemObj.setItemDescription(item.getItemDescription());
                itemObj.setItemDiscount(item.getItemDiscount());
                itemObj.setItemDiscountType(item.getItemDiscountType());
                itemObj.setBusinessProfileCategory(item.getBusinessProfileCategory());
                itemObj.getBusinessProfileCategory().setBusinessProfileCategoryId(
                        new BusinessProfileCategoryPK(itemObj.getBusinessProfileCategory().getBusinessProfile().getBusinessProId(), itemObj.getBusinessProfileCategory().getBusinessCategory().getBusinessCategoryId())
                );
                addFeaturesToItem(item);
                addImagesToItem(item, files);

//                Set<ItemItemFeatureOpt> itemItemFeatureOpts = new HashSet<>();
//                Set<ItemItemFeatureOpt> itemItemFeatureOptsObj = new HashSet<>();
//                for (ItemItemFeature itemItemFeature : item.getItemItemFeatures()) {
//                    itemItemFeatureOpts.add(new ItemItemFeatureOpt(itemItemFeature.getItemItemFeatureId(), itemItemFeature.getItem(), itemItemFeature.getItemFeature()));
//                }
//
//                for (ItemItemFeature itemItemFeature : itemObj.getItemItemFeatures()) {
//                    itemItemFeatureOptsObj.add(new ItemItemFeatureOpt(itemItemFeature.getItemItemFeatureId(), itemItemFeature.getItem(), itemItemFeature.getItemFeature()));
//                }

                HashSet<ItemItemFeature> itemItemFeatures = new HashSet<>(item.getItemItemFeatures());
                itemItemFeatures.retainAll(itemObj.getItemItemFeatures());
                Set<ItemItemFeature> itemItemFeaturesSetRemove = new HashSet<>(itemObj.getItemItemFeatures());
                itemItemFeaturesSetRemove.removeAll(itemItemFeatures);
                itemObj.setItemItemFeatures(item.getItemItemFeatures());

                HashSet<ItemImg> itemImgs = new HashSet<>(item.getItemImgs());
                itemImgs.retainAll(itemObj.getItemImgs());
                Set<ItemImg> itemImgsSetRemove = new HashSet<>(itemObj.getItemImgs());
                itemImgsSetRemove.removeAll(itemImgs);
//                Set<ItemItemFeature> itemItemFeaturesSetRemove = new HashSet<>();
//                for (ItemItemFeatureOpt itemItemFeatureOpt : itemItemFeaturesSetRemoveOpt) {
//                    itemItemFeaturesSetRemove.add(new ItemItemFeature(itemItemFeatureOpt.getItemItemFeatureId(), itemItemFeatureOpt.getItem(), itemItemFeatureOpt.getItemFeature()));
//                }
                if (itemItemFeaturesSetRemove.size() > 0) {
                    itemItemFeatureR.deleteAll(itemItemFeaturesSetRemove);
                }
                if (itemImgsSetRemove.size() > 0) {
//                    itemImgR.deleteAll(itemImgsSetRemove);
                }
//                for (ItemItemFeature itemItemFeature : itemObj.getItemItemFeatures()) {
//                    try {
//                        itemItemFeatureR.save(itemItemFeature);
//                    } catch (ConstraintViolationException e) {
//                        e.printStackTrace();
//                    }
//                }

                return new ItemDTO(itemR.save(itemObj), false);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Something went wrong");
        }
    }

    private void addImagesToItem(Item item, MultipartFile[] files) {
        try {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addFeaturesToItem(Item item) {
        for (ItemItemFeature itemItemFeature : item.getItemItemFeatures()) {
            if (itemItemFeature.getItemFeature().getItemFeatureId().equals("0")) {
//                LocalDateTime localDateTime = LocalDateTime.now();
//                String format = localDateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
                ItemFeature itemFeatureObj = itemItemFeature.getItemFeature();
                itemFeatureObj.setItemFeatureId("ITF" + itemFeatureObj.getName().replace(" ", "_") + itemFeatureObj.getBusinessCategory().getBusinessCategoryId());
                ItemFeature itemFeature = itemFeatureR.save(itemItemFeature.getItemFeature());
                itemItemFeature.setItemFeature(itemFeature);
            }
            itemItemFeature.setItemItemFeatureId(new ItemItemFeaturePK(item.getItemId(), itemItemFeature.getItemFeature().getItemFeatureId()));
            itemItemFeature.setItem(item);
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
