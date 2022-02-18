package lk.dbay.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lk.dbay.entity.*;
import lk.dbay.entity.item.PackageItem;
import lk.dbay.entity.item.PackageItemItem;
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
public class PackageItemDTO extends DateTimeDTO {

    private String packageItemId;
    private ItemPackageDTO itemPackage;
    private List<PackageItemItemDTO> packageItemItems;

    public PackageItemDTO(PackageItem packageItem) {
        if (packageItem != null) {
            this.packageItemId = packageItem.getPackageItemId();
//            this.name = item.getItemPackage().getName();
        }
    }

    @Tolerate
    public void setItemPackage(PackageItem packageItem) {
        if (packageItem.getItemPackage() != null)
            this.itemPackage = new ItemPackageDTO(packageItem.getItemPackage());
    }

    public PackageItemDTO setItemPackageToPackageItem(ItemPackageDTO itemPackageDTO) {
        this.packageItemId = itemPackageDTO.getItemPackageId();
//        this.name = itemPackageDTO.getName();
        this.setItemPackage(itemPackageDTO);
        return this;
    }

    @Tolerate
    public void setPackageItemItems(PackageItem packageItem) {
        this.packageItemItems = new ArrayList<>();
        for (PackageItemItem packageItemItem : packageItem.getPackageItemItems()) {
            this.packageItemItems.add(new PackageItemItemDTO(packageItemItem));
        }
    }
}
