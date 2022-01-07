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
public class ItemPackageDTO extends DateTimeDTO {

    private String itemPackageId;
    private String name;
    private String description;
    private int itemPackageCount;
    private double price;
    private double discount;
    private String discountType;
    private List<ItemItemPackageDTO> itemItemPackages;
    private List<ItemPackageImgDTO> itemPackageImgs;
    private List<ItemPackageItemPackageFeatureDTO> itemPackageItemPackageFeatures;
    private BusinessProfileCategoryDTO businessProfileCategory;


    public ItemPackageDTO(ItemPackage itemPackage) {
        if (itemPackage != null) {
            this.itemPackageId = itemPackage.getItemPackageId();
            this.name = itemPackage.getName();
            this.description = itemPackage.getDescription();
            this.price = itemPackage.getPrice();
            this.discount = itemPackage.getDiscount();
            this.discountType = itemPackage.getDiscountType();
        }
    }

    public void setItemItemPackages(ItemPackage itemPackage, boolean needImage) {
        this.itemItemPackages = new ArrayList<>();
        for (ItemItemPackage itemItemPackage : itemPackage.getItemItemPackages()) {
            this.itemItemPackages.add(new ItemItemPackageDTO(itemItemPackage.getItem(), itemItemPackage.getItemPackage(), needImage));
        }
    }

    public void setItemPackageImgs(ItemPackage itemPackage) {
        this.itemPackageImgs = new ArrayList<>();
        for (ItemPackageImg itemPackageImg : itemPackage.getItemPackageImgs()) {
            this.itemPackageImgs.add(new ItemPackageImgDTO(itemPackageImg));
        }
    }

    public void setItemPackageItemPackageFeatures(ItemPackage itemPackage) {
        this.itemPackageItemPackageFeatures = new ArrayList<>();
        for (ItemPackageItemPackageFeature itemPackageItemPackageFeature : itemPackage.getItemPackageItemPackageFeatures()) {
            this.itemPackageItemPackageFeatures.add(new ItemPackageItemPackageFeatureDTO(itemPackageItemPackageFeature.getItemPackage(), itemPackageItemPackageFeature.getItemPackageFeature()));
        }
    }

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
