package lk.dbay.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lk.dbay.entity.DateTime;
import lk.dbay.entity.Item;
import lk.dbay.entity.ItemItemPackagePK;
import lk.dbay.entity.ItemPackage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemItemPackageDTO extends DateTimeDTO {

    private String name;
    private ItemDTO item;
    private ItemPackageDTO itemPackage;
    private List<ItemDTO> items;
    private List<ItemPackageDTO> itemPackages;

    public ItemItemPackageDTO(Item item, ItemPackage itemPackage, boolean needImage) {
        if (item != null) {
            this.name = item.getName();
            this.item = new ItemDTO(item, needImage);
        }
        if (itemPackage != null)
            this.itemPackage = new ItemPackageDTO(itemPackage);
    }

    public ItemItemPackageDTO(List<Item> items, List<ItemPackage> itemPackages, boolean needImage) {
        this.items = new ArrayList<>();
        this.itemPackages = new ArrayList<>();
        for (Item item : items) {
            ItemDTO itemDTO = new ItemDTO(item, needImage);
            item.getBusinessProfileCategory().setBusinessCategory(null);
            itemDTO.setBusinessProfileCategory(item);
            itemDTO.setOrderDetail(new OrderDetailDTO());
            this.items.add(itemDTO);
        }
        for (ItemPackage itemPackage : itemPackages) {
            ItemPackageDTO itemPackageDTO = new ItemPackageDTO(itemPackage);
            itemPackage.getBusinessProfileCategory().setBusinessCategory(null);
            itemPackageDTO.setBusinessProfileCategory(itemPackage);
            itemPackageDTO.setItemPackageImgs(itemPackage);
            itemPackageDTO.setOrderDetail(new OrderDetailDTO());
//            itemPackageDTO.setBusinessProfileCategory(new BusinessProfileCategoryDTO(itemPackage.getBusinessProfileCategory().getBusinessProfile(), null));
            this.itemPackages.add(itemPackageDTO);
        }

    }
}
