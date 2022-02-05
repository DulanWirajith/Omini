package lk.dbay.service.impl;

import lk.dbay.dto.*;
import lk.dbay.entity.BusinessProfileCategoryPK;
import lk.dbay.entity.item.*;
import lk.dbay.repository.ItemPackageImageR;
import lk.dbay.repository.ItemPackageR;
import lk.dbay.repository.ItemPackageReviewR;
import lk.dbay.repository.ItemPackageReviewResponseR;
import lk.dbay.service.ItemPackageS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ItemPackageSImpl implements ItemPackageS {

    @Autowired
    private ItemPackageR itemPackageR;
    @Autowired
    private ItemPackageImageR itemPackageImageR;
    @Autowired
    private ItemPackageReviewR itemPackageReviewR;
    @Autowired
    private ItemPackageReviewResponseR itemPackageReviewResponseR;

    @Override
    public ItemPackageImageDTO getItemPackageImage(String id) {
        Optional<ItemPackageImage> itemImgOptional = itemPackageImageR.findById(id);
        if (itemImgOptional.isPresent()) {
            ItemPackageImage itemPackageImage = itemImgOptional.get();
            return new ItemPackageImageDTO(itemPackageImage);
        }
        return null;
    }

    @Override
    public boolean setItemPackageAvailable(String itemId) {
        Optional<ItemPackage> itemOptional = itemPackageR.findById(itemId);
        if (itemOptional.isPresent()) {
            ItemPackage itemPackage = itemOptional.get();
            itemPackage.setAvailable(!itemPackage.isAvailable());
            itemPackageR.save(itemPackage);
            return itemPackage.isAvailable();
        }
        return false;
    }

    @Override
    public ItemPackageDTO getItemsPackagesBySearch(String txt, String category) {
        List<ItemPackage> itemsBySearch;
        List<ItemPackage> packagesBySearch;
        ItemPackageDTO itemPackageDTO = new ItemPackageDTO();
        List<ItemPackageDTO> itemPackages = new ArrayList<>();
        List<ItemPackageDTO> items = new ArrayList<>();
        if (category.equals("no")) {
//            itemsBySearch = itemR.getItemsBySearch("%" + txt + "%");
            itemsBySearch = itemPackageR.getItemPackagesBySearchItem("%" + txt + "%");
            packagesBySearch = itemPackageR.getItemPackagesBySearchPackage("%" + txt + "%");
        } else {
            itemsBySearch = itemPackageR.getItemPackagesBySearchItem("%" + txt + "%", category);
            packagesBySearch = itemPackageR.getItemPackagesBySearchPackage("%" + txt + "%", category);
        }
        for (ItemPackage itemBySearch : itemsBySearch) {
            ItemPackageDTO itemPackageDTOObj = new ItemPackageDTO(itemBySearch);
            itemPackageDTOObj.setBusinessProfileCategory(itemBySearch);
            itemPackageDTOObj.setItemPackageImages(itemBySearch);
            itemPackageDTOObj.setOrderDetail(new OrderDetailDTO());
            items.add(itemPackageDTOObj);
        }

        for (ItemPackage packageBySearch : packagesBySearch) {
            ItemPackageDTO itemPackageDTOObj = new ItemPackageDTO(packageBySearch);
            itemPackageDTOObj.setBusinessProfileCategory(packageBySearch);
            itemPackageDTOObj.setItemPackageImages(packageBySearch);
            itemPackageDTOObj.setOrderDetail(new OrderDetailDTO());
            itemPackages.add(itemPackageDTOObj);
        }

//        for (ItemPackage packagesBySearch : packagesBySearch) {
//            if (packagesBySearch.getItemPackageType().equals("Item")) {
//                ItemPackageDTO itemPackageDTOObj = new ItemPackageDTO(packagesBySearch);
//                itemPackageDTOObj.setBusinessProfileCategory(packagesBySearch);
//                itemPackageDTOObj.setItemPackageImages(packagesBySearch);
//                itemPackageDTOObj.setOrderDetail(new OrderDetailDTO());
//                items.add(itemPackageDTOObj);
//            } else if (packagesBySearch.getItemPackageType().equals("ItemPackage")) {
//                ItemPackageDTO itemPackageDTOObj = new ItemPackageDTO(packagesBySearch);
//                itemPackageDTOObj.setBusinessProfileCategory(packagesBySearch);
//                itemPackageDTOObj.setItemPackageImages(packagesBySearch);
//                itemPackageDTOObj.setOrderDetail(new OrderDetailDTO());
//                itemPackages.add(itemPackageDTOObj);
//            }
//        }
        itemPackageDTO.setItemPackages(itemPackages);
        itemPackageDTO.setItems(items);

        return itemPackageDTO;
    }

    @Override
    public ItemPackageDTO getItemPackageSelected(String itemId, String type) {
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
            ItemDTO itemDTO = null;
            PackageItemDTO packageItemDTO = null;
            if (type.equals("Item")) {
                itemDTO = new ItemDTO().setItemPackageToItem(itemPackageDTO);
            } else if (type.equals("Package")) {
                packageItemDTO = new PackageItemDTO(itemPackage.getPackageItem()).setItemPackageToPackageItem(itemPackageDTO);
                List<PackageItemItemDTO> packageItemItemDTOS = new ArrayList<>();
                for (PackageItemItem packageItemItem : itemPackage.getPackageItem().getPackageItemItems()) {
                    PackageItemItemDTO packageItemItemDTO = new PackageItemItemDTO(packageItemItem.getPackageItem(), packageItemItem.getItem());
                    ItemPackageDTO itemPackageItemDTO = new ItemPackageDTO(packageItemItem.getItem().getItemPackage());
                    itemPackageItemDTO.setBusinessProfileCategory(packageItemItem.getItem().getItemPackage());
                    itemPackageItemDTO.setItemPackageItemPackageFeatures(packageItemItem.getItem().getItemPackage());
                    itemPackageItemDTO.setItemPackageImages(packageItemItem.getItem().getItemPackage());
                    packageItemItemDTO.getItem().setItemPackage(itemPackageItemDTO);
                    packageItemItemDTOS.add(packageItemItemDTO);
                }
                packageItemDTO.setPackageItemItems(packageItemItemDTOS);
            }
            ItemPackageDTO itemPackageDTOObj = new ItemPackageDTO();
            itemPackageDTOObj.setItem(itemDTO);
            itemPackageDTOObj.setPackageItem(packageItemDTO);
            return itemPackageDTOObj;
        }
        return null;
    }

    @Override
    public ItemPackageReviewDTO addItemPackageReview(ItemPackageReview itemPackageReview) {
        LocalDateTime localDateTime = LocalDateTime.now();
        String format = localDateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        itemPackageReview.setItemPackageReviewId("IPR" + format);
        itemPackageReview = itemPackageReviewR.save(itemPackageReview);
        ItemPackageReviewDTO itemPackageReviewDTO = new ItemPackageReviewDTO(itemPackageReview);
        itemPackageReviewDTO.setCustomerProfile(itemPackageReview);
        return itemPackageReviewDTO;
    }

    @Override
    public List<ItemPackageReviewDTO> getItemPackageReviews(String itemId, String customerId) {
        List<ItemPackageReview> itemPackageReviews = itemPackageReviewR.getAllByItemPackage_ItemPackageId(itemId);
//        if (reviewType.equals("Item")) {
//            itemPackageReviews = itemPackageReviewR.getAllByItem_ItemIdAndReviewType(itemId, reviewType);
//        } else if (reviewType.equals("ItemPackage")) {
//            itemPackageReviews = itemPackageReviewR.getAllByItemPackage_ItemPackageId(itemId);
//        }
        List<ItemPackageReviewDTO> itemPackageReviewDTOS = new ArrayList<>();
        if (itemPackageReviews != null) {
            for (ItemPackageReview itemPackageReview : itemPackageReviews) {
                ItemPackageReviewDTO itemPackageReviewDTO = new ItemPackageReviewDTO(itemPackageReview);
                List<ItemPackageReviewResponse> responses = itemPackageReviewResponseR.getAllByItemPackageReview_ItemPackageReviewId(itemPackageReview.getItemPackageReviewId());
                for (ItemPackageReviewResponse reviewResponse : responses) {
                    if (reviewResponse.getCustomerProfile().getCustomerProId().equals(customerId)) {
                        itemPackageReviewDTO.setResponseByMe(new ItemPackageReviewResponseDTO(reviewResponse));
                    }
                    if (reviewResponse.getResponse().equals("like")) {
                        itemPackageReviewDTO.setLikeCount(itemPackageReviewDTO.getLikeCount() + 1);
                    } else if (reviewResponse.getResponse().equals("dislike")) {
                        itemPackageReviewDTO.setDislikeCount(itemPackageReviewDTO.getDislikeCount() + 1);
                    }
                }
                itemPackageReviewDTO.setCustomerProfile(itemPackageReview);
                itemPackageReviewDTOS.add(itemPackageReviewDTO);
            }
        }
        return itemPackageReviewDTOS;
    }
}
