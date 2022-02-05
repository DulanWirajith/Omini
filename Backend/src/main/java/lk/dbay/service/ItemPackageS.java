package lk.dbay.service;

import lk.dbay.dto.ItemDTO;
import lk.dbay.dto.ItemPackageDTO;
import lk.dbay.dto.ItemPackageImageDTO;
import lk.dbay.dto.ItemPackageReviewDTO;
import lk.dbay.entity.item.ItemPackageReview;

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

    ItemPackageReviewDTO addItemPackageReview(ItemPackageReview itemPackageReview);

    List<ItemPackageReviewDTO> getItemPackageReviews(String itemId, String customerId);

//    ItemReviewDTO addItemPackageReview(ItemReview itemReview);

//    List<ItemReviewDTO> getItemPackageReviews(String itemId, String customerId, String reviewType);

//    ItemReviewResponseDTO addItemPackageReviewResponse(ItemReviewResponse itemReviewResponse);

//    boolean setItemPackageFavourite(String customerId, String itemId);
}
