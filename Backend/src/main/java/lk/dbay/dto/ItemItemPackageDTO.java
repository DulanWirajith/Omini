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

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemItemPackageDTO extends DateTimeDTO {

    private String name;
    private ItemDTO item;
    private ItemPackageDTO itemPackage;

    public ItemItemPackageDTO(Item item, ItemPackage itemPackage, boolean needImage) {
        this.name = item.getItemTitle();
        this.item = new ItemDTO(item, needImage);
        this.itemPackage = new ItemPackageDTO(itemPackage);
    }
}
