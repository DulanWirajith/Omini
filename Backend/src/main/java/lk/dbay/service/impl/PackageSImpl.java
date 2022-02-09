package lk.dbay.service.impl;

import lk.dbay.dto.*;
import lk.dbay.entity.*;
import lk.dbay.entity.item.*;
import lk.dbay.repository.*;
import lk.dbay.service.PackageS;
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
public class PackageSImpl implements PackageS {

    @Autowired
    private PackageR packageR;
    @Autowired
    private ItemPackageItemPackageFeatureR itemPackageItemPackageFeatureR;
    @Autowired
    private ItemPackageImageR itemPackageImageR;
    @Autowired
    private ItemPackageFeatureR itemPackageFeatureR;
    @Autowired
    private ItemPackageR itemPackageR;
    @Autowired
    private PackageItemItemR packageItemItemR;
    @Value("${image.path}")
    private String filePath;

    @Override
    @Transactional
    public PackageItemDTO addPackage(PackageItem packageItem, MultipartFile[] files) throws Exception {
        try {
            LocalDateTime localDateTime = LocalDateTime.now();
            String format = localDateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            BusinessProfileCategory businessProfileCategory = packageItem.getItemPackage().getBusinessProfileCategory();
            packageItem.setPackageItemId("IPK" + businessProfileCategory.getBusinessProfile().getBusinessProId() + format);
            packageItem.getItemPackage().setItemPackageId(packageItem.getPackageItemId());
            packageItem.getItemPackage().setItemPackageType("Package");
            businessProfileCategory.setBusinessProfileCategoryId(
                    new BusinessProfileCategoryPK(businessProfileCategory.getBusinessProfile().getBusinessProId(), businessProfileCategory.getBusinessCategory().getBusinessCategoryId())
            );
//            itemPackage.setQuantity(-1);
            addItemsToPackageItem(packageItem);
            addFeaturesToPackageItem(packageItem);
            addImagesToPackageItem(packageItem, files);
            itemPackageR.save(packageItem.getItemPackage());
            packageItem = packageR.save(packageItem);
            PackageItemDTO packageItemDTO = new PackageItemDTO(packageItem);
            packageItemDTO.setItemPackage(packageItem);
            packageItemDTO.getItemPackage().setBusinessProfileCategory(packageItem.getItemPackage());
            packageItemDTO.getItemPackage().setItemPackageImages(packageItem.getItemPackage());
            return packageItemDTO;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Something went wrong");
        }
    }

    @Override
    @Transactional
    public PackageItemDTO updatePackage(PackageItem packageItem, MultipartFile[] files, String itemPackageId) throws Exception {
        try {
            Optional<PackageItem> packageItemOptional = packageR.findById(itemPackageId);
            if (packageItemOptional.isPresent()) {
                PackageItem packageItemObj = packageItemOptional.get();
                ItemPackage itemPackageObj = packageItemObj.getItemPackage();
                itemPackageObj.setName(packageItem.getItemPackage().getName());
                itemPackageObj.setDescription(packageItem.getItemPackage().getDescription());
                itemPackageObj.setQuantity(packageItem.getItemPackage().getQuantity());
                itemPackageObj.setMakeToOrder(packageItem.getItemPackage().isMakeToOrder());
                itemPackageObj.setPrice(packageItem.getItemPackage().getPrice());
                itemPackageObj.setDiscount(packageItem.getItemPackage().getDiscount());
                itemPackageObj.setDiscountType(packageItem.getItemPackage().getDiscountType());
                itemPackageObj.setConfirmed(packageItem.getItemPackage().isConfirmed());
//                addItemsToItemPackage(itemPackage);
//                addImagesToItemPackage(itemPackage, files);
                BusinessProfileCategory businessProfileCategory = packageItem.getItemPackage().getBusinessProfileCategory();
                businessProfileCategory.setBusinessProfileCategoryId(
                        new BusinessProfileCategoryPK(businessProfileCategory.getBusinessProfile().getBusinessProId(), businessProfileCategory.getBusinessCategory().getBusinessCategoryId())
                );
                itemPackageObj.setBusinessProfileCategory(businessProfileCategory);

                HashSet<PackageItemItem> packageItemItems = new HashSet<>(packageItem.getPackageItemItems());
                packageItemItems.retainAll(packageItemObj.getPackageItemItems());
                Set<PackageItemItem> packageItemItemsSetRemove = new HashSet<>(packageItemObj.getPackageItemItems());
                packageItemItemsSetRemove.removeAll(packageItemItems);
                Set<PackageItemItem> packageItemItemsSetAdd = new HashSet<>(packageItem.getPackageItemItems());
                packageItemItemsSetAdd.removeAll(packageItemItems);
                packageItemObj.setPackageItemItems(packageItemItemsSetAdd);
                addItemsToPackageItem(packageItemObj);

                HashSet<ItemPackageItemPackageFeature> itemPackageItemPackageFeatures = new HashSet<>(packageItem.getItemPackage().getItemPackageItemPackageFeatures());
                itemPackageItemPackageFeatures.retainAll(packageItemObj.getItemPackage().getItemPackageItemPackageFeatures());
                Set<ItemPackageItemPackageFeature> itemPackageItemPackageFeaturesSetRemove = new HashSet<>(packageItemObj.getItemPackage().getItemPackageItemPackageFeatures());
                itemPackageItemPackageFeaturesSetRemove.removeAll(itemPackageItemPackageFeatures);
                Set<ItemPackageItemPackageFeature> itemPackageItemPackageFeaturesSetAdd = new HashSet<>(packageItem.getItemPackage().getItemPackageItemPackageFeatures());
                itemPackageItemPackageFeaturesSetAdd.removeAll(itemPackageItemPackageFeatures);
                packageItemObj.getItemPackage().setItemPackageItemPackageFeatures(itemPackageItemPackageFeaturesSetAdd);
                addFeaturesToPackageItem(packageItemObj);

                HashSet<ItemPackageImage> itemPackageImgs = new HashSet<>(packageItem.getItemPackage().getItemPackageImages());
                itemPackageImgs.retainAll(packageItemObj.getItemPackage().getItemPackageImages());
                Set<ItemPackageImage> itemPackageImgsSetRemove = new HashSet<>(packageItemObj.getItemPackage().getItemPackageImages());
                itemPackageImgsSetRemove.removeAll(itemPackageImgs);
                Set<ItemPackageImage> itemPackageImgsSetAdd = new HashSet<>(packageItem.getItemPackage().getItemPackageImages());
                itemPackageImgsSetAdd.removeAll(itemPackageImgs);
                packageItemObj.getItemPackage().setItemPackageImages(itemPackageImgsSetAdd);
                addImagesToPackageItem(packageItemObj, files);

                if (packageItemItemsSetRemove.size() > 0) {
                    packageItemItemR.deleteAll(packageItemItemsSetRemove);
                }
                if (itemPackageItemPackageFeaturesSetRemove.size() > 0) {
                    itemPackageItemPackageFeatureR.deleteAll(itemPackageItemPackageFeaturesSetRemove);
                }
                if (itemPackageImgsSetRemove.size() > 0) {
                    itemPackageImageR.deleteAll(itemPackageImgsSetRemove);
                }

                packageItemObj = packageR.save(packageItemObj);
                PackageItemDTO packageItemDTO = new PackageItemDTO(packageItemObj);
                packageItemDTO.setItemPackage(packageItemObj);
                packageItemDTO.getItemPackage().setBusinessProfileCategory(packageItemObj.getItemPackage());
                packageItemDTO.getItemPackage().setItemPackageImages(packageItemObj.getItemPackage());
                return packageItemDTO;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Something went wrong");
        }
    }

    private void addItemsToPackageItem(PackageItem packageItem) {
        for (PackageItemItem packageItemItem : packageItem.getPackageItemItems()) {
            packageItemItem.setPackageItemItemId(new PackageItemItemPK(packageItem.getPackageItemId(), packageItemItem.getItem().getItemId()));
            packageItemItem.setPackageItem(packageItem);
        }
    }

    private void addFeaturesToPackageItem(PackageItem packageItem) {
        for (ItemPackageItemPackageFeature itemPackageItemPackageFeature : packageItem.getItemPackage().getItemPackageItemPackageFeatures()) {
            ItemPackageFeature itemPackageFeatureObj = itemPackageItemPackageFeature.getItemPackageFeature();
            itemPackageFeatureObj.setItemPackageFeatureId("ITPF" + itemPackageFeatureObj.getName().replace(" ", "_") + packageItem.getItemPackage().getBusinessProfileCategory().getBusinessCategory().getBusinessCategoryId());
            ItemPackageFeature itemPackageFeature = itemPackageFeatureR.save(itemPackageItemPackageFeature.getItemPackageFeature());
            itemPackageItemPackageFeature.setItemPackageFeature(itemPackageFeature);
            itemPackageItemPackageFeature.setItemPackageItemPackageFeatureId(new ItemPackageItemPackageFeaturePK(packageItem.getPackageItemId(), itemPackageItemPackageFeature.getItemPackageFeature().getItemPackageFeatureId()));
            itemPackageItemPackageFeature.setItemPackage(packageItem.getItemPackage());
        }
    }

    private void addImagesToPackageItem(PackageItem packageItem, MultipartFile[] files) {
        try {
//            Set<ItemImg> itemImgs = new HashSet<>();
            for (ItemPackageImage itemPackageImage : packageItem.getItemPackage().getItemPackageImages()) {
                itemPackageImage.setItemPackage(packageItem.getItemPackage());
            }
            LocalDateTime localDateTime = LocalDateTime.now();
            String format = localDateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            int i = 0;
            String filePathCur = filePath + "\\package";
            for (MultipartFile file : files) {
                ItemPackageImage itemPackageImage = new ItemPackageImage();
                itemPackageImage.setItemPackageImageId("ITPKGIMG" + ++i + format);
                Path root = Paths.get(filePathCur);
                if (!Files.exists(root)) {
                    Files.createDirectories(Paths.get(filePathCur));
                }
                try {
                    Files.copy(file.getInputStream(), root.resolve(file.getOriginalFilename()));
                } catch (FileAlreadyExistsException e) {
//                    e.printStackTrace();
                }
                itemPackageImage.setImageName("package/" + StringUtils.cleanPath(file.getOriginalFilename()));
                itemPackageImage.setImageType(file.getContentType());
                itemPackageImage.setItemPackage(packageItem.getItemPackage());
                packageItem.getItemPackage().getItemPackageImages().add(itemPackageImage);
            }
//            itemPackage.setItemImgs(itemImgs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<PackageItemDTO> getPackageItemsOrdered(String businessProfileId, String businessCategoryId, int start, int limit) {
        List<PackageItem> packageItemList = packageR.getPackageItemsOrdered(new BusinessProfileCategoryPK(businessProfileId, businessCategoryId), PageRequest.of(start, limit));
        List<PackageItemDTO> packageItemDTOS = new ArrayList<>();
        for (PackageItem packageItem : packageItemList) {
//            PackageItemDTO packageItemDTO = new PackageItemDTO(packageItem);
//            packageItemDTO.getItemPackage().setBusinessProfileCategory(packageItem.getItemPackage());
//            packageItemDTO.getItemPackage().setItemPackageImages(packageItem.getItemPackage());
            ItemPackageDTO itemPackageDTO = new ItemPackageDTO(packageItem.getItemPackage());
            itemPackageDTO.setItemPackageImages(packageItem.getItemPackage());
            packageItemDTOS.add(new PackageItemDTO().setItemPackageToPackageItem(itemPackageDTO));
        }
        return packageItemDTOS;
    }

//    @Override
//    public PackageItemDTO getPackageItemSelected(String packageItemId) {
//        Optional<PackageItem> packageItemOptional = packageR.findById(packageItemId);
//        if (packageItemOptional.isPresent()) {
//            PackageItem packageItem = packageItemOptional.get();
//            PackageItemDTO packageItemDTO = new PackageItemDTO(packageItem);
//            packageItemDTO.setItemPackage(new ItemPackageDTO(packageItem.getItemPackage()));
//            ItemPackageDTO itemPackageDTO = packageItemDTO.getItemPackage();
//            itemPackageDTO.setBusinessProfileCategory(packageItem.getItemPackage());
//            itemPackageDTO.setItemPackageItemPackageFeatures(packageItem.getItemPackage());
//            itemPackageDTO.setItemPackageImages(packageItem.getItemPackage());
//            itemPackageDTO.setOrderDetail(new OrderDetailDTO());
//
//            List<PackageItemItemDTO> packageItemItemDTOS = new ArrayList<>();
//            for (PackageItemItem packageItemItem : packageItem.getPackageItemItems()) {
////                PackageItem packageItemObj = packageItemItem.getPackageItem();
//                PackageItemItemDTO packageItemItemDTO = new PackageItemItemDTO(packageItemItem.getPackageItem(),packageItemItem.getItem());
////                packageItemItemDTO.setItemId(packageItemItem.getItem().getItemId());
////                packageItemItemDTO.setName(packageItemItem.getItem().getItemPackage().getName());
////                packageItemItemDTO.setPackageItem(packageItemDTO);
////                packageItemItemDTO.setItem(packageItemItem.getItem());
//
//                ItemPackageDTO itemPackageItemDTO = new ItemPackageDTO(packageItemItem.getItem().getItemPackage());
//                itemPackageItemDTO.setBusinessProfileCategory(packageItemItem.getItem().getItemPackage());
//                itemPackageItemDTO.setItemPackageItemPackageFeatures(packageItemItem.getItem().getItemPackage());
//                itemPackageItemDTO.setItemPackageImages(packageItemItem.getItem().getItemPackage());
//                packageItemItemDTO.getItem().setItemPackage(itemPackageItemDTO);
//////            itemDTO.setItemFeatures(item);
//////            if (itemPackage.getItemPackageType().equals("Item")) {
//////                itemPackageDTO.getItemDTO().setItemCategory(itemPackage.getItem());
//////            }
////                itemPackageDTO.setOrderDetail(new OrderDetailDTO());
//
//                packageItemItemDTOS.add(packageItemItemDTO);
//            }
//            packageItemDTO.setPackageItemItems(packageItemItemDTOS);
//            return packageItemDTO;
//        }
//        return null;
//    }

    @Override
    public boolean removePackage(String packageItemId) throws Exception {
        try {
            packageR.deleteById(packageItemId);
            itemPackageR.deleteById(packageItemId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("You cannot remove this package, since it is used.");
        }
    }
}
