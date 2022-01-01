package lk.dbay.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lk.dbay.entity.ItemFeature;
import lk.dbay.entity.ItemPackageFeature;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemPackageFeatureDTO extends DateTimeDTO {

    private String itemPackageFeatureId;
    private String name;
    private String description;

    public ItemPackageFeatureDTO(@NonNull ItemPackageFeature itemPackageFeature) {
        this.itemPackageFeatureId = itemPackageFeature.getItemPackageFeatureId();
        this.name = itemPackageFeature.getName();
        this.description = itemPackageFeature.getDescription();
    }
}
