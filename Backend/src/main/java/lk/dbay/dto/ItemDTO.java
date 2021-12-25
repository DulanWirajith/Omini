package lk.dbay.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lk.dbay.entity.*;
import lombok.*;

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
    private String itemTitle;
    private String name;
    private int itemQty;
    private double itemPrice;
    private String itemDescription;
    private double itemDiscount;
    private String itemDiscountType;
    private ItemCategoryDTO itemCategory;
    private boolean itemAvailable;
    private BusinessProfileCategoryDTO businessProfileCategory;
    private List<ItemItemFeatureDTO> itemItemFeatures;
    private List<ItemFeatureDTO> itemFeatures;
    private List<ItemImgDTO> itemImgs;

//    private byte[] itemImg;
//    private String itemImgName;
//    private String itemImgType;

    public ItemDTO(@NonNull Item item, boolean needImage) {
        this.name = item.getItemTitle();
        this.itemId = item.getItemId();
        this.itemTitle = item.getItemTitle();
        this.itemQty = item.getItemQty();
        this.itemPrice = item.getItemPrice();
        this.itemDescription = item.getItemDescription();
        this.itemDiscount = item.getItemDiscount();
        this.itemDiscountType = item.getItemDiscountType();
        this.itemAvailable = item.isItemAvailable();
        if (needImage) {
            List<ItemImgDTO> itemImgs = new ArrayList<>();
            for (ItemImg itemImg : item.getItemImgs()) {
                itemImgs.add(new ItemImgDTO(itemImg));
            }
            this.itemImgs = itemImgs;
//            this.itemImgName = item.getItemImgName();
//            this.itemImg = item.getItemImg();
//            this.itemImgType = item.getItemImgType();
        }
    }

    public ItemDTO(@NonNull Item item, boolean needImage, @NonNull ItemCategory itemCategory) {
        this(item, needImage);
        this.itemCategory = new ItemCategoryDTO(itemCategory);
    }

    public ItemDTO(@NonNull Item item, boolean needImage, @NonNull BusinessProfileCategory businessProfileCategory, @NonNull Set<ItemItemFeature> itemItemFeatures) {
        this(item, needImage);
        this.businessProfileCategory = new BusinessProfileCategoryDTO(businessProfileCategory.getBusinessProfile(), businessProfileCategory.getBusinessCategory());
        this.itemItemFeatures = new ArrayList<>();
        for (ItemItemFeature itemItemFeature : itemItemFeatures) {
            this.itemItemFeatures.add(new ItemItemFeatureDTO(item, itemItemFeature.getItemFeature()));
        }
    }
}
