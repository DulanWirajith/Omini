package lk.dbay.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lk.dbay.entity.ItemFeature;
import lk.dbay.entity.ItemPackage;
import lombok.*;

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

    public ItemPackageDTO(@NonNull ItemPackage itemPackage) {
        this.itemPackageId = itemPackage.getItemPackageId();
        this.name = itemPackage.getName();
        this.description = itemPackage.getDescription();
        this.price = itemPackage.getPrice();
        this.discount = itemPackage.getDiscount();
        this.discountType = itemPackage.getDiscountType();
    }
}
