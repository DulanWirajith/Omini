package lk.dbay.service;

import lk.dbay.dto.*;
import lk.dbay.entity.item.ItemPackageReview;
import lk.dbay.entity.item.ItemPackageReviewResponse;

import java.util.List;

public interface ItemPackageS {

//    List<ItemPackageDTO> getItemsPackageBusinessCategory(String businessProfileId, String businessCategoryId);

    ItemPackageImageDTO getItemPackageImage(String id);

//    List<ItemPackageDTO> getItemPackagesOrdered(String businessProfileId, String businessCategoryId, int start, int limit);
//
//    ItemPackageDTO getItemPackageSelected(String itemId);
//
    boolean setItemPackageAvailable(String itemId);

    ItemPackageDTO getItemsPackagesBySearch(String txt, String category, String customerId);

    ItemPackageDTO getItemPackageSelected(String itemId, String type, String customerId);

    ItemPackageReviewDTO addItemPackageReview(ItemPackageReview itemPackageReview);

    List<ItemPackageReviewDTO> getItemPackageReviews(String itemId, String customerId);

    ItemPackageReviewResponseDTO addItemPackageResponse(ItemPackageReviewResponse itemReviewResponse);

    boolean setItemPackageFavourite(String customerId, String itemId);

    ItemPackageDTO getFavItemPackages(String customerId);

//    ItemReviewDTO addItemPackageReview(ItemReview itemReview);

//    List<ItemReviewDTO> getItemPackageReviews(String itemId, String customerId, String reviewType);

//    ItemReviewResponseDTO addItemPackageReviewResponse(ItemReviewResponse itemReviewResponse);

//    boolean setItemPackageFavourite(String customerId, String itemId);
}
