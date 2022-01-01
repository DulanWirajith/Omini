package lk.dbay.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lk.dbay.entity.DateTime;
import lk.dbay.entity.ItemPackage;
import lk.dbay.entity.ItemPackageFeature;
import lk.dbay.entity.ItemPackageItemPackageFeaturePK;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Objects;

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
        this.name = itemPackageFeature.getName();
        this.itemPackage = new ItemPackageDTO(itemPackage);
        this.itemPackageFeature = new ItemPackageFeatureDTO(itemPackageFeature);
    }
}
