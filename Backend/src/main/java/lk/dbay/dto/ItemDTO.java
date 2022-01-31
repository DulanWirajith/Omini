package lk.dbay.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lk.dbay.entity.*;
import lk.dbay.entity.item.Item;
import lk.dbay.entity.item.ItemPackage;
import lk.dbay.entity.item.ItemPackageItemPackageFeature;
import lombok.*;
import lombok.experimental.Tolerate;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemDTO extends DateTimeDTO {

    private String itemId;
    private String name;
    //    private int quantity;
//    private boolean makeToOrder;
//    private double price;
//    private String description;
//    private double discount;
//    private double discountedPrice;
//    private String discountType;
//    private boolean confirmed;
//    private boolean available;
    private ItemCategoryDTO itemCategory;
    private ItemPackageDTO itemPackage;
    //    private boolean itemAvailable;
    private BusinessProfileCategoryDTO businessProfileCategory;
    private List<ItemPackageItemPackageFeatureDTO> itemPackageItemPackageFeatures;
    //    private List<ItemFeatureDTO> itemFeatures;
//    private List<ItemImgDTO> itemImgs;
//    private List<ItemReviewDTO> itemReviews;
    private OrderDetailDTO orderDetail;
//    private boolean favourite;

//    private byte[] itemImg;
//    private String itemImgName;
//    private String itemImgType;

    public ItemDTO(Item item) {
        if (item != null) {
            this.itemId = item.getItemId();
            this.name = item.getItemPackage().getName();
        }
    }

    public void setItemCategory(Item item) {
        if (item.getItemCategory() != null)
            this.itemCategory = new ItemCategoryDTO(item.getItemCategory());
    }

    @Tolerate
    public void setItemPackage(Item item) {
        if (item.getItemPackage() != null)
            this.itemPackage = new ItemPackageDTO(item.getItemPackage());
    }

    public ItemDTO setItemPackageToItem(ItemPackageDTO itemPackageDTO) {
        this.itemId = itemPackageDTO.getItemPackageId();
        this.name = itemPackageDTO.getName();
        this.setItemPackage(itemPackageDTO);
        return this;
    }

    @Tolerate
    public void setBusinessProfileCategory(Item item) {
        if (item.getItemPackage().getBusinessProfileCategory() != null)
            this.businessProfileCategory = new BusinessProfileCategoryDTO(item.getItemPackage().getBusinessProfileCategory().getBusinessProfile(), item.getItemPackage().getBusinessProfileCategory().getBusinessCategory());
    }

    public void setItemPackageItemPackageFeatures(Item item) {
        if (item.getItemPackage().getItemPackageItemPackageFeatures() != null) {
            this.itemPackageItemPackageFeatures = new ArrayList<>();
            for (ItemPackageItemPackageFeature itemPackageItemPackageFeature : item.getItemPackage().getItemPackageItemPackageFeatures()) {
                this.itemPackageItemPackageFeatures.add(new ItemPackageItemPackageFeatureDTO(
                        itemPackageItemPackageFeature.getItemPackage(),
                        itemPackageItemPackageFeature.getItemPackageFeature()
                ));
            }
        }
    }

//    public void setItemItemFeatures(Item item) {
//        this.itemItemFeatures = new ArrayList<>();
//        for (ItemItemFeature itemItemFeature : item.getItemItemFeatures()) {
//            this.itemItemFeatures.add(new ItemItemFeatureDTO(item, itemItemFeature.getItemFeature()));
//        }
//    }

//    public void setItemReviews(Item item) {
//        this.itemReviews = new ArrayList<>();
//        for (ItemReview itemReview : item.getItemReviews()) {
//            this.itemReviews.add(new ItemReviewDTO(itemReview));
//        }
//    }

//    public void setItemFeatures(Item item) {
////        this.itemFeatures = itemFeatures;
//    }
//
//    public void setItemImgs(Item item) {
////        this.itemImgs = itemImgs;
//    }

//    public ItemDTO(@NonNull Item item, boolean needImage, @NonNull ItemCategory itemCategory) {
//        this(item, needImage);
//        this.itemCategory = new ItemCategoryDTO(itemCategory);
//    }

//    public ItemDTO(@NonNull Item item, boolean needImage, @NonNull BusinessProfileCategory businessProfileCategory, @NonNull Set<ItemItemFeature> itemItemFeatures) {
//        this(item, needImage);
//        this.businessProfileCategory = new BusinessProfileCategoryDTO(businessProfileCategory.getBusinessProfile(), businessProfileCategory.getBusinessCategory());
//        this.itemItemFeatures = new ArrayList<>();
//        for (ItemItemFeature itemItemFeature : itemItemFeatures) {
//            this.itemItemFeatures.add(new ItemItemFeatureDTO(item, itemItemFeature.getItemFeature()));
//        }
//    }
}