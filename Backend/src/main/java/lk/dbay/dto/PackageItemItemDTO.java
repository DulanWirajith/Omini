package lk.dbay.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lk.dbay.entity.item.Item;
import lk.dbay.entity.item.PackageItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PackageItemItemDTO extends DateTimeDTO {

    private String itemId;
    private String name;
    private PackageItemDTO packageItem;
    private ItemDTO item;

    public PackageItemItemDTO(PackageItem packageItem, Item item) {
        if (packageItem != null)
            this.packageItem = new PackageItemDTO(packageItem);
        if (item != null) {
            this.itemId = item.getItemId();
            this.name = item.getItemPackage().getName();
            this.item = new ItemDTO(item);
        }
    }
}
