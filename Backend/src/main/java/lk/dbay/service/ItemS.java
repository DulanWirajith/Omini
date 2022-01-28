package lk.dbay.service;

import lk.dbay.dto.*;
import lk.dbay.entity.Item;
import lk.dbay.entity.ItemReview;
import lk.dbay.entity.ItemReviewResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ItemS {
    ItemDTO addItem(Item item, MultipartFile[] files) throws Exception;

    ItemDTO updateItem(Item item, MultipartFile[] files, String itemId) throws Exception;

    List<ItemDTO> getItems();

    List<ItemDTO> getItemsBusinessCategory(String businessProfileId, String businessCategoryId);

    ItemImgDTO getItemImg(String id);

    List<ItemDTO> getItemsOrdered(String businessProfileId, String businessCategoryId, int start, int limit);

    ItemDTO getItemSelected(String itemId);

    boolean setItemAvailable(String itemId);

    ItemItemPackageDTO getItemsPackagesBySearch(String txt, String category);

    ItemReviewDTO addItemReview(ItemReview itemReview);

    List<ItemReviewDTO> getItemReviews(String itemId, String customerId, String reviewType);

    ItemReviewResponseDTO addItemReviewResponse(ItemReviewResponse itemReviewResponse);

    boolean removeItem(String itemId) throws Exception;

    boolean setItemFavourite(String customerId, String itemId);
}
