package lk.dbay.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lk.dbay.entity.item.Item;
import lk.dbay.entity.item.PackageItem;
import lk.dbay.entity.item.PackageItemItem;
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
    private int quantity;

    public PackageItemItemDTO(PackageItemItem packageItemItem) {
        this.quantity = packageItemItem.getQuantity();
        if (packageItemItem.getPackageItem() != null)
            this.packageItem = new PackageItemDTO(packageItemItem.getPackageItem());
        if (packageItemItem.getItem() != null) {
            this.itemId = packageItemItem.getItem().getItemId();
            this.name = packageItemItem.getItem().getItemPackage().getName();
            this.item = new ItemDTO(packageItemItem.getItem());
        }
    }

    public void setItem(Item item) {
        if (item != null) {
            this.item = new ItemDTO(item);
        }
    }
}
