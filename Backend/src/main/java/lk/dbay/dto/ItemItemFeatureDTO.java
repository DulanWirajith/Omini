package lk.dbay.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lk.dbay.entity.DateTime;
import lk.dbay.entity.Item;
import lk.dbay.entity.ItemFeature;
import lk.dbay.entity.ItemItemFeaturePK;
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
public class ItemItemFeatureDTO extends DateTimeDTO {

    //    private ItemItemFeaturePK itemItemFeatureId;
    private String name;
    private ItemDTO item;
    private ItemFeatureDTO itemFeature;

    public ItemItemFeatureDTO(Item item, ItemFeature itemFeature) {
//        this.item = new ItemDTO(item, true);
        this.name = itemFeature.getName();
        this.itemFeature = new ItemFeatureDTO(itemFeature);
        this.item = new ItemDTO(item, false);
    }
}
