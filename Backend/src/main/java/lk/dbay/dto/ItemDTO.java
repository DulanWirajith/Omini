package lk.dbay.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lk.dbay.entity.*;
import lombok.*;
import lombok.experimental.Tolerate;

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
    private int itemCount;
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

    public ItemDTO(Item item, boolean needImage) {
        if (item != null) {
            this.name = item.getItemTitle();
            this.itemId = item.getItemId();
            this.itemTitle = item.getItemTitle();
            this.itemQty = item.getItemQty();
            this.itemPrice = item.getItemPrice();
            this.itemDescription = item.getItemDescription();
            this.itemDiscountType = item.getItemDiscountType();
            this.itemDiscount = item.getItemDiscount();
//        if (this.itemDiscountType.equals("None")) {
//            this.itemDiscount = "N/A";
//        } else if (this.itemDiscountType.equals("Cash")) {
//            this.itemDiscount = "LKR " + item.getItemDiscount();
//        } else if (this.itemDiscountType.equals("Percentage")) {
//            this.itemDiscount = item.getItemDiscount() + "%";
//        }
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
    }

    public void setItemCategory(Item item) {
        if (item.getItemCategory() != null)
            this.itemCategory = new ItemCategoryDTO(item.getItemCategory());
    }

    @Tolerate
    public void setBusinessProfileCategory(Item item) {
        if (item.getBusinessProfileCategory() != null)
            this.businessProfileCategory = new BusinessProfileCategoryDTO(item.getBusinessProfileCategory().getBusinessProfile(), item.getBusinessProfileCategory().getBusinessCategory());
    }

    public void setItemItemFeatures(Item item) {
        this.itemItemFeatures = new ArrayList<>();
        for (ItemItemFeature itemItemFeature : item.getItemItemFeatures()) {
            this.itemItemFeatures.add(new ItemItemFeatureDTO(item, itemItemFeature.getItemFeature()));
        }
    }

    public void setItemFeatures(Item item) {
//        this.itemFeatures = itemFeatures;
    }

    public void setItemImgs(Item item) {
//        this.itemImgs = itemImgs;
    }

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
