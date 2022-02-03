package lk.dbay.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lk.dbay.entity.item.ItemPackage;
import lk.dbay.entity.item.ItemPackageImage;
import lk.dbay.entity.item.ItemPackageItemPackageFeature;
import lombok.*;
import lombok.experimental.Tolerate;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemPackageDTO extends DateTimeDTO {

    private String itemPackageId;
    private String name;
    private int quantity;
    private boolean makeToOrder;
    private double price;
    private double discountedPrice;
    private String description;
    private double discount;
    private String discountType;
    private boolean confirmed;
    private boolean available;
    //    private List<ItemItemPackageDTO> itemItemPackages;
    private List<ItemPackageImageDTO> itemPackageImages;
    private List<ItemPackageItemPackageFeatureDTO> itemPackageItemPackageFeatures;
    private BusinessProfileCategoryDTO businessProfileCategory;
    private OrderDetailDTO orderDetail;
    private ItemDTO item;
    private PackageItemDTO packageItem;
    private List<ItemPackageDTO> items;
    private List<ItemPackageDTO> itemPackages;
    private String itemPackageType;
//    private boolean favourite;

    public ItemPackageDTO(ItemPackage itemPackage) {
        if (itemPackage != null) {
            this.itemPackageId = itemPackage.getItemPackageId();
            this.name = itemPackage.getName();
            this.quantity = itemPackage.getQuantity();
            this.description = itemPackage.getDescription();
            this.price = itemPackage.getPrice();
            this.discount = itemPackage.getDiscount();
            this.discountType = itemPackage.getDiscountType();
            this.confirmed = itemPackage.isConfirmed();
            this.available = itemPackage.isAvailable();
            this.makeToOrder = itemPackage.isMakeToOrder();
            this.itemPackageType = itemPackage.getItemPackageType();
//            this.favourite = itemPackage.isFavourite();
            if (this.discountType.equals("Cash")) {
                this.discountedPrice = (this.price - this.discount);
            } else if (this.discountType.equals("Percentage")) {
                this.discountedPrice = (this.price * ((100 - this.discount) / 100));
            } else if (this.discountType.equals("None")) {
                this.discountedPrice = this.price;
            }
        }
    }

//    public void setItemItemPackages(ItemPackage itemPackage, boolean needImage) {
//        this.itemItemPackages = new ArrayList<>();
//        for (ItemItemPackage itemItemPackage : itemPackage.getItemItemPackages()) {
//            this.itemItemPackages.add(new ItemItemPackageDTO(itemItemPackage.getItem(), itemItemPackage.getItemPackage(), needImage));
//        }
//    }

    public void setItemPackageImages(ItemPackage itemPackage) {
        this.itemPackageImages = new ArrayList<>();
        for (ItemPackageImage itemPackageImage : itemPackage.getItemPackageImages()) {
            this.itemPackageImages.add(new ItemPackageImageDTO(itemPackageImage));
        }
    }

    public void setItemPackageItemPackageFeatures(ItemPackage itemPackage) {
        this.itemPackageItemPackageFeatures = new ArrayList<>();
        for (ItemPackageItemPackageFeature itemPackageItemPackageFeature : itemPackage.getItemPackageItemPackageFeatures()) {
            this.itemPackageItemPackageFeatures.add(new ItemPackageItemPackageFeatureDTO(itemPackageItemPackageFeature.getItemPackage(), itemPackageItemPackageFeature.getItemPackageFeature()));
        }
    }

    @Tolerate
    public void setBusinessProfileCategory(ItemPackage itemPackage) {
        if (itemPackage.getBusinessProfileCategory() != null)
            this.businessProfileCategory = new BusinessProfileCategoryDTO(itemPackage.getBusinessProfileCategory().getBusinessProfile(), itemPackage.getBusinessProfileCategory().getBusinessCategory());
    }

//    public ItemPackageDTO(@NonNull ItemPackage itemPackage, @NonNull BusinessProfileCategory businessProfileCategory, @NonNull Set<ItemPackageImg> itemPackageImgs) {
//        this(itemPackage);
//        this.itemPackageImgs = new ArrayList<>();
//        this.businessProfileCategory = new BusinessProfileCategoryDTO(businessProfileCategory.getBusinessProfile(), businessProfileCategory.getBusinessCategory());
//        for (ItemPackageImg itemPackageImg : itemPackageImgs) {
//            this.itemPackageImgs.add(new ItemPackageImgDTO(itemPackageImg));
//        }
//    }

//    public ItemPackageDTO(@NonNull ItemPackage itemPackage, @NonNull BusinessProfileCategory businessProfileCategory, @NonNull Set<ItemItemPackage> itemItemPackages, @NonNull Set<ItemPackageItemPackageFeature> itemPackageItemPackageFeatures, @NonNull Set<ItemPackageImg> itemPackageImgs, boolean needImage) {
//        this(itemPackage);
//        this.itemItemPackages = new ArrayList<>();
//        this.businessProfileCategory = new BusinessProfileCategoryDTO(businessProfileCategory.getBusinessProfile(), businessProfileCategory.getBusinessCategory());
//        for (ItemItemPackage itemItemPackage : itemItemPackages) {
//            this.itemItemPackages.add(new ItemItemPackageDTO(itemItemPackage.getItem(), itemItemPackage.getItemPackage(), needImage));
//        }
//        this.itemPackageImgs = new ArrayList<>();
//        for (ItemPackageImg itemPackageImg : itemPackageImgs) {
//            this.itemPackageImgs.add(new ItemPackageImgDTO(itemPackageImg));
//        }
//        this.itemPackageItemPackageFeatures = new ArrayList<>();
//        for (ItemPackageItemPackageFeature itemPackageItemPackageFeature : itemPackageItemPackageFeatures) {
//            this.itemPackageItemPackageFeatures.add(new ItemPackageItemPackageFeatureDTO(itemPackageItemPackageFeature.getItemPackage(), itemPackageItemPackageFeature.getItemPackageFeature()));
//        }
//    }
}