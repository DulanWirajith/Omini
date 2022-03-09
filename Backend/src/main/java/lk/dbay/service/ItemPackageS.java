package lk.dbay.service;

import lk.dbay.dto.*;
import lk.dbay.entity.item.ItemPackageReview;
import lk.dbay.entity.item.ItemPackageReviewResponse;

public interface ItemPackageS {

//    List<ItemPackageDTO> getItemsPackageBusinessCategory(String businessProfileId, String businessCategoryId);

    ItemPackageImageDTO getItemPackageImage(String id);

    //    List<ItemPackageDTO> getItemPackagesOrdered(String businessProfileId, String businessCategoryId, int start, int limit);
//
//    ItemPackageDTO getItemPackageSelected(String itemId);
//
    boolean setItemPackageAvailable(String itemId);

    ItemPackageDTO getItemsPackagesBySearch(String txt, String category, String district, String town, String customerId, double lat, double lon);

    ItemPackageDTO getItemPackageSelected(String itemId, String type, String customerId);

    ItemPackageReviewDTO addItemPackageReview(ItemPackageReview itemPackageReview);

    ItemPackageReviewDTO getItemPackageReviews(String itemId, String customerId);

    ItemPackageReviewResponseDTO addItemPackageResponse(ItemPackageReviewResponse itemReviewResponse);

    boolean setItemPackageFavourite(String customerId, String itemId);

    ItemPackageDTO getFavItemPackages(String customerId);

    ItemPackageReviewDTO updateItemPackageReview(ItemPackageReview itemPackageReview, String reviewId);

    boolean removeItemPackageReview(String reviewId) throws Exception;

//    ItemReviewDTO addItemPackageReview(ItemReview itemReview);

//    List<ItemReviewDTO> getItemPackageReviews(String itemId, String customerId, String reviewType);

//    ItemReviewResponseDTO addItemPackageReviewResponse(ItemReviewResponse itemReviewResponse);

//    boolean setItemPackageFavourite(String customerId, String itemId);
}
