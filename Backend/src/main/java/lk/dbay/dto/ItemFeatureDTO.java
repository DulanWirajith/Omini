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

    private String itemCategoryId;
    private String name;
    private String description;

    public ItemFeatureDTO(@NonNull ItemFeature itemFeature) {
        this.itemCategoryId = itemFeature.getItemFeatureId();
        this.name = itemFeature.getName();
        this.description = itemFeature.getDescription();
    }
}
