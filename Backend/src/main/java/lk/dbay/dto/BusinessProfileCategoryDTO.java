package lk.dbay.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lk.dbay.entity.BusinessCategory;
import lk.dbay.entity.BusinessProfile;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BusinessProfileCategoryDTO extends DateTimeDTO {

    private String name;

    private BusinessProfileDTO businessProfile;

    private BusinessCategoryDTO businessCategory;

    public BusinessProfileCategoryDTO(@NonNull BusinessProfile businessProfile, @NonNull BusinessCategory businessCategory) {
        this.name = businessCategory.getName();
        this.businessProfile = new BusinessProfileDTO(businessProfile);
        this.businessCategory = new BusinessCategoryDTO(businessCategory);
    }
}
