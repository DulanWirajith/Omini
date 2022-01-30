package lk.dbay.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lk.dbay.entity.item.ItemPackage;
import lk.dbay.entity.item.ItemPackageFeature;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemPackageItemPackageFeatureDTO extends DateTimeDTO {

    private String name;

    private ItemPackageDTO itemPackage;
    private ItemPackageFeatureDTO itemPackageFeature;

    public ItemPackageItemPackageFeatureDTO(ItemPackage itemPackage, ItemPackageFeature itemPackageFeature) {
        if (itemPackage != null)
            this.itemPackage = new ItemPackageDTO(itemPackage);
        if (itemPackageFeature != null) {
            this.name = itemPackageFeature.getName();
            this.itemPackageFeature = new ItemPackageFeatureDTO(itemPackageFeature);
        }
    }
}