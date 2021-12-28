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
    private double price;
    private double discount;
    private String discountType;
    private List<ItemItemPackageDTO> itemItemPackages;
    private List<ItemPackageImgDTO> itemPackageImgs;
    private BusinessProfileCategoryDTO businessProfileCategory;


    public ItemPackageDTO(@NonNull ItemPackage itemPackage) {
        this.itemPackageId = itemPackage.getItemPackageId();
        this.name = itemPackage.getName();
        this.description = itemPackage.getDescription();
        this.price = itemPackage.getPrice();
        this.discount = itemPackage.getDiscount();
        this.discountType = itemPackage.getDiscountType();
    }

    public ItemPackageDTO(@NonNull ItemPackage itemPackage, @NonNull BusinessProfileCategory businessProfileCategory, @NonNull Set<ItemPackageImg> itemPackageImgs) {
        this(itemPackage);
        this.itemPackageImgs = new ArrayList<>();
        this.businessProfileCategory = new BusinessProfileCategoryDTO(businessProfileCategory.getBusinessProfile(), businessProfileCategory.getBusinessCategory());
        for (ItemPackageImg itemPackageImg : itemPackageImgs) {
            this.itemPackageImgs.add(new ItemPackageImgDTO(itemPackageImg));
        }
    }

    public ItemPackageDTO(@NonNull ItemPackage itemPackage, @NonNull BusinessProfileCategory businessProfileCategory, @NonNull Set<ItemItemPackage> itemItemPackages, @NonNull Set<ItemPackageImg> itemPackageImgs, boolean needImage) {
        this(itemPackage);
        this.itemItemPackages = new ArrayList<>();
        this.businessProfileCategory = new BusinessProfileCategoryDTO(businessProfileCategory.getBusinessProfile(), businessProfileCategory.getBusinessCategory());
        for (ItemItemPackage itemItemPackage : itemItemPackages) {
            this.itemItemPackages.add(new ItemItemPackageDTO(itemItemPackage.getItem(), itemItemPackage.getItemPackage(), needImage));
        }
        this.itemPackageImgs = new ArrayList<>();
        for (ItemPackageImg itemPackageImg : itemPackageImgs) {
            this.itemPackageImgs.add(new ItemPackageImgDTO(itemPackageImg));
        }
    }
}
