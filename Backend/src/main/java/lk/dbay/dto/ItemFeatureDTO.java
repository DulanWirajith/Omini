package lk.dbay.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lk.dbay.entity.ItemCategory;
import lk.dbay.entity.ItemFeature;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemFeatureDTO extends DateTimeDTO {

    private String itemFeatureId;
    private String name;
    private String description;

    public ItemFeatureDTO(ItemFeature itemFeature) {
        if (itemFeature != null) {
            this.itemFeatureId = itemFeature.getItemFeatureId();
            this.name = itemFeature.getName();
            this.description = itemFeature.getDescription();
        }
    }
}
