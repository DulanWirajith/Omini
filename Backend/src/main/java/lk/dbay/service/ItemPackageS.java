package lk.dbay.service;

import lk.dbay.dto.ItemDTO;
import lk.dbay.dto.ItemPackageDTO;
import lk.dbay.dto.ItemPackageImageDTO;

import java.util.List;

public interface ItemPackageS {

//    List<ItemPackageDTO> getItemsPackageBusinessCategory(String businessProfileId, String businessCategoryId);

    ItemPackageImageDTO getItemPackageImage(String id);

//    List<ItemPackageDTO> getItemPackagesOrdered(String businessProfileId, String businessCategoryId, int start, int limit);
//
//    ItemPackageDTO getItemPackageSelected(String itemId);
//
    boolean setItemPackageAvailable(String itemId);

    ItemPackageDTO getItemsPackagesBySearch(String txt, String category);

    ItemPackageDTO getItemPackageSelected(String itemId, String type);

//    ItemReviewDTO addItemPackageReview(ItemReview itemReview);

//    List<ItemReviewDTO> getItemPackageReviews(String itemId, String customerId, String reviewType);

//    ItemReviewResponseDTO addItemPackageReviewResponse(ItemReviewResponse itemReviewResponse);

//    boolean setItemPackageFavourite(String customerId, String itemId);
}
