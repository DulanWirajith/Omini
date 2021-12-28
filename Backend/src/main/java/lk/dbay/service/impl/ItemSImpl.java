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

import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
            businessProfileCategory.setBusinessProfileCategoryId(
                    new BusinessProfileCategoryPK(businessProfileCategory.getBusinessProfile().getBusinessProId(), businessProfileCategory.getBusinessCategory().getBusinessCategoryId())
            );
            addFeaturesToItem(item);
            addImagesToItem(item, files);
            return new ItemDTO(itemR.save(item), true);
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
//                addFeaturesToItem(item);
//                addImagesToItem(item, files);

                HashSet<ItemItemFeature> itemItemFeatures = new HashSet<>(item.getItemItemFeatures());
                itemItemFeatures.retainAll(itemObj.getItemItemFeatures());
                Set<ItemItemFeature> itemItemFeaturesSetRemove = new HashSet<>(itemObj.getItemItemFeatures());
                itemItemFeaturesSetRemove.removeAll(itemItemFeatures);
                Set<ItemItemFeature> itemItemFeaturesSetAdd = new HashSet<>(item.getItemItemFeatures());
                itemItemFeaturesSetAdd.removeAll(itemItemFeatures);
                itemObj.setItemItemFeatures(itemItemFeaturesSetAdd);
                addFeaturesToItem(itemObj);

                HashSet<ItemImg> itemImgs = new HashSet<>(item.getItemImgs());
                itemImgs.retainAll(itemObj.getItemImgs());
                Set<ItemImg> itemImgsSetRemove = new HashSet<>(itemObj.getItemImgs());
                itemImgsSetRemove.removeAll(itemImgs);
                Set<ItemImg> itemImgsSetAdd = new HashSet<>(item.getItemImgs());
                itemImgsSetAdd.removeAll(itemImgs);
                itemObj.setItemImgs(itemImgsSetAdd);
                addImagesToItem(itemObj, files);

                if (itemItemFeaturesSetRemove.size() > 0) {
                    itemItemFeatureR.deleteAll(itemItemFeaturesSetRemove);
                }
                if (itemImgsSetRemove.size() > 0) {
                    itemImgR.deleteAll(itemImgsSetRemove);
                }

                return new ItemDTO(itemR.save(itemObj), true);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Something went wrong");
        }
    }

    private void addImagesToItem(Item item, MultipartFile[] files) {
        try {
//            Set<ItemImg> itemImgs = new HashSet<>();
            for (ItemImg itemImg : item.getItemImgs()) {
                itemImg.setItem(item);
            }
            LocalDateTime localDateTime = LocalDateTime.now();
            String format = localDateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            int i = 0;
            String filePath = "C:\\xampp\\htdocs\\Dbay";
            for (MultipartFile file : files) {
                ItemImg itemImg = new ItemImg();
                itemImg.setItemImgId("ITIMG" + ++i + format);
//                itemImg.setItemImg(file.getBytes());
                Path root = Paths.get(filePath);
//                if (!Files.exists(root)) {
////                    Files.createDirectories(Paths.get(filePath));
//                }
                try {
                    Files.copy(file.getInputStream(), root.resolve(file.getOriginalFilename()));
                } catch (FileAlreadyExistsException e) {
                    e.printStackTrace();
                }
                itemImg.setItemImgName(StringUtils.cleanPath(file.getOriginalFilename()));
//                itemImg.setItemImgPath("C:\\xampp\\htdocs\\Dbay\\" + itemImg.getItemImgName());
                itemImg.setItemImgType(file.getContentType());
                itemImg.setItem(item);
                item.getItemImgs().add(itemImg);
            }
//            item.setItemImgs(itemImgs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addFeaturesToItem(Item item) {
        for (ItemItemFeature itemItemFeature : item.getItemItemFeatures()) {
//            if (itemItemFeature.getItemFeature().getItemFeatureId().equals("0")) {
//                LocalDateTime localDateTime = LocalDateTime.now();
//                String format = localDateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            ItemFeature itemFeatureObj = itemItemFeature.getItemFeature();
            itemFeatureObj.setItemFeatureId("ITF" + itemFeatureObj.getName().replace(" ", "_") + item.getBusinessProfileCategory().getBusinessCategory().getBusinessCategoryId());
            ItemFeature itemFeature = itemFeatureR.save(itemItemFeature.getItemFeature());
            itemItemFeature.setItemFeature(itemFeature);
//            }
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
            itemDTOS.add(new ItemDTO(item, false));
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
