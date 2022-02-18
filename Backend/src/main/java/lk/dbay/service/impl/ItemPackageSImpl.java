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
    @Autowired
    private ItemPackageDAO itemPackageDAO;

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
    public ItemPackageDTO getItemsPackagesBySearch(String txt, String category, String district, String town, String customerId) {
        ItemPackageDTO itemPackageDTO = new ItemPackageDTO();
        List<ItemPackageDTO> itemPackages = new ArrayList<>();
        List<ItemPackageDTO> items = new ArrayList<>();
//        if (category.equals("no")) {
////            itemsBySearch = itemR.getItemsBySearch("%" + txt + "%");
//            itemsBySearch = itemPackageR.getItemPackagesBySearchItem("%" + txt + "%");
//            packagesBySearch = itemPackageR.getItemPackagesBySearchPackage("%" + txt + "%");
//        } else {
//            itemsBySearch = itemPackageR.getItemPackagesBySearchItem("%" + txt + "%", category);
//            packagesBySearch = itemPackageR.getItemPackagesBySearchPackage("%" + txt + "%", category);
//        }
        List<ItemPackage> itemsBySearch = itemPackageDAO.getItemPackages("Item", txt, category, district, town);
        List<ItemPackage> packagesBySearch = itemPackageDAO.getItemPackages("Package", txt, category, district, town);
        setItemPackageDTO(itemsBySearch, items, customerId);
        setItemPackageDTO(packagesBySearch, itemPackages, customerId);
        itemPackageDTO.setItemPackages(itemPackages);
        itemPackageDTO.setItems(items);

        return itemPackageDTO;
    }

    private void setItemPackageDTO(List<ItemPackage> itemsBySearch, List<ItemPackageDTO> itemPackages, String customerId) {
        for (ItemPackage itemBySearch : itemsBySearch) {
            ItemPackageDTO itemPackageDTOObj = new ItemPackageDTO(itemBySearch);
            if (!customerId.equals("0")) {
                Optional<ItemPackageFavourite> itemPackageFavourite = itemPackageFavouriteR.getByCustomerProfile_CustomerProIdAndItemPackage_ItemPackageId(customerId, itemBySearch.getItemPackageId());
                itemPackageDTOObj.setFavourite(itemPackageFavourite.isPresent());
            }
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
            if (!customerId.equals("0")) {
                Optional<ItemPackageFavourite> itemPackageFavourite = itemPackageFavouriteR.getByCustomerProfile_CustomerProIdAndItemPackage_ItemPackageId(customerId, itemId);
                itemPackageDTO.setFavourite(itemPackageFavourite.isPresent());
            }
//            itemDTO.setItemFeatures(item);
//            if (itemPackage.getItemPackageType().equals("Item")) {
//                itemPackageDTO.getItemDTO().setItemCategory(itemPackage.getItem());
//            }
            itemPackageDTO.setOrderDetail(new OrderDetailDTO());
            ItemDTO itemDTO = null;
            PackageItemDTO packageItemDTO = null;
            if (type.equals("Item")) {
                itemPackageDTO.setItemCategory(itemPackage);
                itemDTO = new ItemDTO(itemPackage.getItem()).setItemPackageToItem(itemPackageDTO);
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
    public ItemPackageReviewDTO updateItemPackageReview(ItemPackageReview itemPackageReview, String reviewId) {
        Optional<ItemPackageReview> itemPackageReviewOptional = itemPackageReviewR.findById(reviewId);
        if (itemPackageReviewOptional.isPresent()) {
            ItemPackageReview itemPackageReviewObj = itemPackageReviewOptional.get();
            itemPackageReviewObj.setDescription(itemPackageReview.getDescription());
            itemPackageReviewObj.setRating(itemPackageReview.getRating());
            itemPackageReviewObj = itemPackageReviewR.save(itemPackageReviewObj);
            //            itemPackageReviewDTO.setCustomerProfile(itemPackageReviewObj);
            return new ItemPackageReviewDTO(itemPackageReviewObj);
        }
        return null;
    }

    @Override
    public boolean removeItemPackageReview(String reviewId) throws Exception {
        try {
            itemPackageReviewR.deleteById(reviewId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("You cannot remove this item, since it is used.");
        }
    }

    @Override
    public ItemPackageReviewDTO getItemPackageReviews(String itemId, String customerId) {
        List<ItemPackageReview> itemPackageReviews = itemPackageReviewR.getAllByItemPackage_ItemPackageId(itemId);
        List<ItemPackageReviewDTO> itemPackageReviewDTOS = new ArrayList<>();
        ItemPackageReviewDTO itemPackageReviewDTOObj = new ItemPackageReviewDTO();
        double tempRating = 0;
        if (itemPackageReviews != null) {
            for (ItemPackageReview itemPackageReview : itemPackageReviews) {
                ItemPackageReviewDTO itemPackageReviewDTO = new ItemPackageReviewDTO(itemPackageReview);
                tempRating += itemPackageReview.getRating();
//                List<ItemPackageReviewResponse> responses = itemPackageReviewResponseR.getAllByItemPackageReview_ItemPackageReviewId(itemPackageReview.getItemPackageReviewId());
                for (ItemPackageReviewResponse reviewResponse : itemPackageReview.getItemPackageReviewResponses()) {
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
            if (itemPackageReviews.size() > 0) {
                itemPackageReviewDTOObj.setRating1((tempRating / itemPackageReviews.size()));
                itemPackageReviewDTOObj.setRating2((int) itemPackageReviewDTOObj.getRating1());
            }
        }
        itemPackageReviewDTOObj.setItemPackageReviews(itemPackageReviewDTOS);
        return itemPackageReviewDTOObj;
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
        setItemPackageDTO(itemsBySearch, items, "0");
        setItemPackageDTO(packagesBySearch, itemPackages, "0");
        itemPackageDTO.setItemPackages(itemPackages);
        itemPackageDTO.setItems(items);
        return itemPackageDTO;
    }

}
