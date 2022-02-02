package lk.dbay.service.impl;

import lk.dbay.dto.*;
import lk.dbay.entity.*;
import lk.dbay.entity.item.*;
import lk.dbay.repository.*;
import lk.dbay.service.ItemS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class ItemSImpl implements ItemS {

    @Autowired
    private ItemR itemR;
    @Autowired
    private ItemPackageR itemPackageR;
    @Autowired
    private ItemPackageFeatureR itemPackageFeatureR;
    @Autowired
    private ItemPackageItemPackageFeatureR itemPackageItemPackageFeatureR;
    @Autowired
    private ItemPackageImageR itemPackageImageR;
    @Value("${image.path}")
    private String filePath;

    @Override
    @Transactional
    public ItemDTO addItem(Item item, MultipartFile[] files) throws Exception {
        try {
            LocalDateTime localDateTime = LocalDateTime.now();
            String format = localDateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            BusinessProfileCategory businessProfileCategory = item.getItemPackage().getBusinessProfileCategory();
            item.setItemId("ITM" + businessProfileCategory.getBusinessProfile().getBusinessProId() + format);
            item.getItemPackage().setItemPackageId(item.getItemId());
            item.getItemPackage().setItemPackageType("Item");
            businessProfileCategory.setBusinessProfileCategoryId(
                    new BusinessProfileCategoryPK(businessProfileCategory.getBusinessProfile().getBusinessProId(), businessProfileCategory.getBusinessCategory().getBusinessCategoryId())
            );
            addFeaturesToItem(item);
            addImagesToItem(item, files);
            itemPackageR.save(item.getItemPackage());
            item = itemR.save(item);
            ItemDTO itemDTO = new ItemDTO(item);
            itemDTO.setItemPackage(item);
            itemDTO.getItemPackage().setItemPackageImages(item.getItemPackage());
            return itemDTO;
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
                ItemPackage itemPackageObj = itemObj.getItemPackage();
                itemPackageObj.setName(item.getItemPackage().getName());
                itemPackageObj.setQuantity(item.getItemPackage().getQuantity());
                itemPackageObj.setPrice(item.getItemPackage().getPrice());
                itemPackageObj.setMakeToOrder(item.getItemPackage().isMakeToOrder());
                itemPackageObj.setQuantity(item.getItemPackage().getQuantity());
                itemPackageObj.setDescription(item.getItemPackage().getDescription());
                itemPackageObj.setDiscount(item.getItemPackage().getDiscount());
                itemPackageObj.setDiscountType(item.getItemPackage().getDiscountType());
                itemPackageObj.setBusinessProfileCategory(item.getItemPackage().getBusinessProfileCategory());
                itemPackageObj.getBusinessProfileCategory().setBusinessProfileCategoryId(
                        new BusinessProfileCategoryPK(itemPackageObj.getBusinessProfileCategory().getBusinessProfile().getBusinessProId(), itemPackageObj.getBusinessProfileCategory().getBusinessCategory().getBusinessCategoryId())
                );
//                addFeaturesToItem(item);
//                addImagesToItem(item, files);

                HashSet<ItemPackageItemPackageFeature> itemPackageItemPackageFeatures = new HashSet<>(item.getItemPackage().getItemPackageItemPackageFeatures());
                itemPackageItemPackageFeatures.retainAll(itemObj.getItemPackage().getItemPackageItemPackageFeatures());
                Set<ItemPackageItemPackageFeature> itemPackageItemPackageFeaturesSetRemove = new HashSet<>(itemObj.getItemPackage().getItemPackageItemPackageFeatures());
                itemPackageItemPackageFeaturesSetRemove.removeAll(itemPackageItemPackageFeatures);
                Set<ItemPackageItemPackageFeature> itemPackageItemPackageFeaturesSetAdd = new HashSet<>(item.getItemPackage().getItemPackageItemPackageFeatures());
                itemPackageItemPackageFeaturesSetAdd.removeAll(itemPackageItemPackageFeatures);
                itemObj.getItemPackage().setItemPackageItemPackageFeatures(itemPackageItemPackageFeaturesSetAdd);
                addFeaturesToItem(itemObj);

                HashSet<ItemPackageImage> itemPackageImages = new HashSet<>(item.getItemPackage().getItemPackageImages());
                itemPackageImages.retainAll(itemObj.getItemPackage().getItemPackageImages());
                Set<ItemPackageImage> itemPackageImagesSetRemove = new HashSet<>(itemObj.getItemPackage().getItemPackageImages());
                itemPackageImagesSetRemove.removeAll(itemPackageImages);
                Set<ItemPackageImage> itemPackageImagesAdd = new HashSet<>(item.getItemPackage().getItemPackageImages());
                itemPackageImagesAdd.removeAll(itemPackageImages);
                itemObj.getItemPackage().setItemPackageImages(itemPackageImagesAdd);
                addImagesToItem(itemObj, files);

                if (itemPackageItemPackageFeaturesSetRemove.size() > 0) {
                    itemPackageItemPackageFeatureR.deleteAll(itemPackageItemPackageFeaturesSetRemove);
                }
                if (itemPackageImagesSetRemove.size() > 0) {
                    itemPackageImageR.deleteAll(itemPackageImagesSetRemove);
                }
                itemObj = itemR.save(itemObj);
                ItemDTO itemDTO = new ItemDTO(itemObj);
                itemDTO.setItemPackage(itemObj);
                itemDTO.getItemPackage().setItemPackageImages(itemObj.getItemPackage());
                return itemDTO;
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
            for (ItemPackageImage itemPackageImage : item.getItemPackage().getItemPackageImages()) {
                itemPackageImage.setItemPackage(item.getItemPackage());
            }
            LocalDateTime localDateTime = LocalDateTime.now();
            String format = localDateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            int i = 0;
//            String filePath = "C:\\xampp\\htdocs\\Dbay";
            String filePathCur = filePath + "\\item";
            for (MultipartFile file : files) {
                ItemPackageImage itemPackageImage = new ItemPackageImage();
                itemPackageImage.setItemPackageImageId("ITIMG" + ++i + format);
//                itemImg.setItemImg(file.getBytes());
                Path root = Paths.get(filePathCur);
                if (!Files.exists(root)) {
                    Files.createDirectories(Paths.get(filePathCur));
                }
                try {
                    Files.copy(file.getInputStream(), root.resolve(file.getOriginalFilename()));
                } catch (FileAlreadyExistsException e) {
//                    e.printStackTrace();
                }
                itemPackageImage.setImageName("item/" + StringUtils.cleanPath(file.getOriginalFilename()));
//                itemImg.setItemImgPath("C:\\xampp\\htdocs\\Dbay\\" + itemImg.getItemImgName());
                itemPackageImage.setImageType(file.getContentType());
                itemPackageImage.setItemPackage(item.getItemPackage());
                item.getItemPackage().getItemPackageImages().add(itemPackageImage);
            }
//            item.setItemImgs(itemImgs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addFeaturesToItem(Item item) {
        for (ItemPackageItemPackageFeature itemPackageItemPackageFeature : item.getItemPackage().getItemPackageItemPackageFeatures()) {
//            if (itemItemFeature.getItemFeature().getItemFeatureId().equals("0")) {
//                LocalDateTime localDateTime = LocalDateTime.now();
//                String format = localDateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            ItemPackageFeature itemPackageFeatureObj = itemPackageItemPackageFeature.getItemPackageFeature();
            itemPackageFeatureObj.setItemPackageFeatureId("ITF" + itemPackageFeatureObj.getName().replace(" ", "_") + item.getItemPackage().getBusinessProfileCategory().getBusinessCategory().getBusinessCategoryId());
            ItemPackageFeature itemPackageFeature = itemPackageFeatureR.save(itemPackageItemPackageFeature.getItemPackageFeature());
            itemPackageItemPackageFeature.setItemPackageFeature(itemPackageFeature);
//            }
            itemPackageItemPackageFeature.setItemPackageItemPackageFeatureId(new ItemPackageItemPackageFeaturePK(item.getItemId(), itemPackageItemPackageFeature.getItemPackageFeature().getItemPackageFeatureId()));
            itemPackageItemPackageFeature.setItemPackage(item.getItemPackage());
        }
    }

//    @Override
//    public List<ItemDTO> getItems() {
//        List<Item> itemList = itemR.findAll();
//        List<ItemDTO> itemDTOS = new ArrayList<>();
//        for (Item item : itemList) {
//            itemDTOS.add(new ItemDTO(item));
//        }
//        return itemDTOS;
//    }

    @Override
    public boolean removeItem(String itemId) throws Exception {
        try {
            itemR.deleteById(itemId);
            return true;
        } catch (Exception e) {
            throw new Exception("You cannot remove this item, since it is used.");
        }
    }

    @Override
    public List<ItemDTO> getItemsOrdered(String businessProfileId, String businessCategoryId, int start, int limit) {
        List<Item> itemList = itemR.getItemsOrdered(new BusinessProfileCategoryPK(businessProfileId, businessCategoryId), PageRequest.of(start, limit));
        List<ItemDTO> itemDTOS = new ArrayList<>();
        for (Item item : itemList) {
//            ItemDTO itemDTO = ;
            ItemPackageDTO itemPackageDTO = new ItemPackageDTO(item.getItemPackage());
            itemPackageDTO.setItemPackageImages(item.getItemPackage());
//            itemDTO.setItemPackage(itemPackageDTO);
            itemDTOS.add(new ItemDTO().setItemPackageToItem(itemPackageDTO));
        }
        return itemDTOS;
    }

    @Override
    public ItemDTO getItemSelected(String itemId) {
        Optional<ItemPackage> itemPackageOptional = itemPackageR.findById(itemId);
        if (itemPackageOptional.isPresent()) {
            ItemPackage itemPackage = itemPackageOptional.get();
//            List<ItemFeature> itemFeatureRAll = itemFeatureR.getAllByBusinessCategory_BusinessCategoryIdAndConfirmed(item.getBusinessProfileCategory().getBusinessCategory().getBusinessCategoryId(), true);
//            List<ItemFeatureDTO> itemFeatureDTOS = new ArrayList<>();
//            for (ItemFeature itemFeature : itemFeatureRAll) {
//                itemFeatureDTOS.add(new ItemFeatureDTO(itemFeature));
//            }
            ItemPackageDTO itemPackageDTO = new ItemPackageDTO(itemPackage);
            itemPackageDTO.setBusinessProfileCategory(itemPackage);
            itemPackageDTO.setItemPackageItemPackageFeatures(itemPackage);
            itemPackageDTO.setItemPackageImages(itemPackage);
//            itemDTO.setItemFeatures(item);
//            if (itemPackage.getItemPackageType().equals("Item")) {
//                itemPackageDTO.getItemDTO().setItemCategory(itemPackage.getItem());
//            }
            itemPackageDTO.setOrderDetail(new OrderDetailDTO());
            return new ItemDTO().setItemPackageToItem(itemPackageDTO);
        }
        return null;
    }

    @Override
    public List<ItemDTO> getItemsBusinessCategory(String businessProfileId, String businessCategoryId) {
        List<Item> itemsList = itemR.getAllByItemPackage_BusinessProfileCategory_BusinessProfileCategoryId(new BusinessProfileCategoryPK(businessProfileId, businessCategoryId));
        List<ItemDTO> itemDTOS = new ArrayList<>();
        for (Item item : itemsList) {
            itemDTOS.add(new ItemDTO(item));
        }
        return itemDTOS;
    }

//    @Override
//    public ItemDTO getItemSelected(String itemId) {
//        Optional<Item> itemOptional = itemR.findById(itemId);
//        if (itemOptional.isPresent()) {
//            Item item = itemOptional.get();
////            List<ItemFeature> itemFeatureRAll = itemFeatureR.getAllByBusinessCategory_BusinessCategoryIdAndConfirmed(item.getBusinessProfileCategory().getBusinessCategory().getBusinessCategoryId(), true);
////            List<ItemFeatureDTO> itemFeatureDTOS = new ArrayList<>();
////            for (ItemFeature itemFeature : itemFeatureRAll) {
////                itemFeatureDTOS.add(new ItemFeatureDTO(itemFeature));
////            }
//            ItemDTO itemDTO = new ItemDTO(item);
//            itemDTO.setBusinessProfileCategory(item);
//            itemDTO.setItemPackageItemPackageFeatures(item);
////            itemDTO.setItemFeatures(item);
//            itemDTO.setItemCategory(item);
//            itemDTO.setOrderDetail(new OrderDetailDTO());
//            return itemDTO;
//        }
//        return null;
//    }
}
