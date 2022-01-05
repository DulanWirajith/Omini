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

    public BusinessProfileCategoryDTO(BusinessProfile businessProfile, BusinessCategory businessCategory) {
        if (businessProfile != null) {
            this.businessProfile = new BusinessProfileDTO(businessProfile);
        }
        if (businessCategory != null) {
            this.name = businessCategory.getName();
            this.businessCategory = new BusinessCategoryDTO(businessCategory);
        }
    }
}
