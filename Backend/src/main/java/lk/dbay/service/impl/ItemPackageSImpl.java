package lk.dbay.service.impl;

import lk.dbay.dto.*;
import lk.dbay.entity.CustomerProfile;
import lk.dbay.entity.item.*;
import lk.dbay.repository.*;
import lk.dbay.service.ItemPackageS;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private ItemPackageFavouriteR itemPackageFavouriteR;

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
    public ItemPackageDTO getItemsPackagesBySearch(String txt, String category, String customerId) {
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
        setItemPackageDTO(itemsBySearch, items);
        setItemPackageDTO(packagesBySearch, itemPackages);
//        for (ItemPackage itemBySearch : itemsBySearch) {
//            ItemPackageDTO itemPackageDTOObj = new ItemPackageDTO(itemBySearch);
//            itemPackageDTOObj.setBusinessProfileCategory(itemBySearch);
//            itemPackageDTOObj.setItemPackageImages(itemBySearch);
//            itemPackageDTOObj.setOrderDetail(new OrderDetailDTO());
//            items.add(itemPackageDTOObj);
//        }
//
//        for (ItemPackage packageBySearch : packagesBySearch) {
//            ItemPackageDTO itemPackageDTOObj = new ItemPackageDTO(packageBySearch);
//            itemPackageDTOObj.setBusinessProfileCategory(packageBySearch);
//            itemPackageDTOObj.setItemPackageImages(packageBySearch);
//            itemPackageDTOObj.setOrderDetail(new OrderDetailDTO());
//            itemPackages.add(itemPackageDTOObj);
//        }

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

    private void setItemPackageDTO(List<ItemPackage> itemsBySearch, List<ItemPackageDTO> itemPackages) {
        for (ItemPackage itemBySearch : itemsBySearch) {
            ItemPackageDTO itemPackageDTOObj = new ItemPackageDTO(itemBySearch);
            itemPackageDTOObj.setBusinessProfileCategory(itemBySearch);
            itemPackageDTOObj.setItemPackageImages(itemBySearch);
            itemPackageDTOObj.setOrderDetail(new OrderDetailDTO());
            itemPackages.add(itemPackageDTOObj);
        }
    }

    @Override
    public ItemPackageDTO getItemPackageSelected(String itemId, String type, String customerId) {
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
            Optional<ItemPackageFavourite> itemPackageFavourite = itemPackageFavouriteR.getByCustomerProfile_CustomerProIdAndItemPackage_ItemPackageId(customerId, itemId);
            itemPackageDTO.setFavourite(itemPackageFavourite.isPresent());
//            itemDTO.setItemFeatures(item);
//            if (itemPackage.getItemPackageType().equals("Item")) {
//                itemPackageDTO.getItemDTO().setItemCategory(itemPackage.getItem());
//            }
            itemPackageDTO.setOrderDetail(new OrderDetailDTO());
            ItemDTO itemDTO = null;
            PackageItemDTO packageItemDTO = null;
            if (type.equals("Item")) {
                itemPackageDTO.setItemCategory(itemPackage);
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

    @Override
    public ItemPackageReviewResponseDTO addItemPackageResponse(ItemPackageReviewResponse itemPackageReviewResponse) {
        if (!itemPackageReviewResponse.getResponse().equals("remove")) {
            if (itemPackageReviewResponse.getItemPackageReviewResponseId().equals("")) {
                LocalDateTime localDateTime = LocalDateTime.now();
                String format = localDateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
                itemPackageReviewResponse.setItemPackageReviewResponseId("IRRE" + format);
                return new ItemPackageReviewResponseDTO(itemPackageReviewResponseR.save(itemPackageReviewResponse));
            } else {
                Optional<ItemPackageReviewResponse> optionalItemReviewResponse = itemPackageReviewResponseR.findById(itemPackageReviewResponse.getItemPackageReviewResponseId());
                if (optionalItemReviewResponse.isPresent()) {
                    ItemPackageReviewResponse itemReviewResponseObj = optionalItemReviewResponse.get();
                    itemReviewResponseObj.setResponse(itemPackageReviewResponse.getResponse());
                    return new ItemPackageReviewResponseDTO(itemPackageReviewResponseR.save(itemPackageReviewResponse));
                }
            }
        } else {
            itemPackageReviewResponseR.deleteById(itemPackageReviewResponse.getItemPackageReviewResponseId());
            return new ItemPackageReviewResponseDTO(itemPackageReviewResponse);
        }
        return null;
    }

    @Override
    public boolean setItemPackageFavourite(String customerId, String itemId) {
        Optional<ItemPackageFavourite> itemFavourite = itemPackageFavouriteR.findById(new ItemPackageFavouritePK(customerId, itemId));
        if (itemFavourite.isPresent()) {
            itemPackageFavouriteR.deleteById(new ItemPackageFavouritePK(customerId, itemId));
            return false;
        } else {
            ItemPackageFavourite itemPackageFavourite = new ItemPackageFavourite();
            itemPackageFavourite.setItemPackageFavouriteId(new ItemPackageFavouritePK(customerId, itemId));
            CustomerProfile customerProfile = new CustomerProfile();
            customerProfile.setCustomerProId(customerId);
            itemPackageFavourite.setCustomerProfile(customerProfile);
            ItemPackage itemPackage = new ItemPackage();
            itemPackage.setItemPackageId(itemId);
            itemPackageFavourite.setItemPackage(itemPackage);
            itemPackageFavouriteR.save(itemPackageFavourite);
            return true;
        }
//        return new CustomerItemFavouriteDTO(customerItemFavourite.getCustomerProfile(), customerItemFavourite.getItem());
    }

    @Override
    public ItemPackageDTO getFavItemPackages(String customerId) {
//        List<ItemPackage> itemPackageFavourites=new ArrayList<>();
        List<ItemPackage> itemsBySearch = new ArrayList<>();
        List<ItemPackage> packagesBySearch = new ArrayList<>();
        ItemPackageDTO itemPackageDTO = new ItemPackageDTO();
        List<ItemPackageDTO> itemPackages = new ArrayList<>();
        List<ItemPackageDTO> items = new ArrayList<>();
        List<ItemPackageFavourite> itemPackageFavouriteList = itemPackageFavouriteR.getAllByCustomerProfile_CustomerProId(customerId);
        for (ItemPackageFavourite itemPackageFavourite : itemPackageFavouriteList) {
            if (itemPackageFavourite.getItemPackage().getItemPackageType().equals("Item")) {
                itemsBySearch.add(itemPackageFavourite.getItemPackage());
            } else if (itemPackageFavourite.getItemPackage().getItemPackageType().equals("Package")) {
                packagesBySearch.add(itemPackageFavourite.getItemPackage());
            }
        }
        setItemPackageDTO(itemsBySearch, items);
        setItemPackageDTO(packagesBySearch, itemPackages);
        itemPackageDTO.setItemPackages(itemPackages);
        itemPackageDTO.setItems(items);
        return itemPackageDTO;
    }
}
