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
    private String name;
    private int quantity;
    private double price;
    private String description;
    private double discount;
    private double discountedPrice;
    private String discountType;
    private boolean confirmed;
    private boolean available;
    private ItemCategoryDTO itemCategory;
    private boolean itemAvailable;
    private BusinessProfileCategoryDTO businessProfileCategory;
    private List<ItemItemFeatureDTO> itemItemFeatures;
    private List<ItemFeatureDTO> itemFeatures;
    private List<ItemImgDTO> itemImgs;
    private OrderDetailDTO orderDetail;

//    private byte[] itemImg;
//    private String itemImgName;
//    private String itemImgType;

    public ItemDTO(Item item, boolean needImage) {
        if (item != null) {
            this.itemId = item.getItemId();
            this.name = item.getName();
            this.quantity = item.getQuantity();
            this.price = item.getPrice();
            this.description = item.getDescription();
            this.discountType = item.getDiscountType();
            this.discount = item.getDiscount();
            this.confirmed = item.isConfirmed();
            this.available = item.isAvailable();
            if (this.discountType.equals("Cash")) {
                this.discountedPrice = (this.price - this.discount);
            } else if (this.discountType.equals("Percentage")) {
                this.discountedPrice = (this.price * ((100 - this.discount) / 100));
            } else if (this.discountType.equals("None")) {
                this.discountedPrice = this.price;
            }
//        if (this.itemDiscountType.equals("None")) {
//            this.itemDiscount = "N/A";
//        } else if (this.itemDiscountType.equals("Cash")) {
//            this.itemDiscount = "LKR " + item.getItemDiscount();
//        } else if (this.itemDiscountType.equals("Percentage")) {
//            this.itemDiscount = item.getItemDiscount() + "%";
//        }
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
