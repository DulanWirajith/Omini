package lk.dbay.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BusinessProfileCategoryDTO extends DateTimeDTO {

    private String name;

    private BusinessProfileDTO businessProfile;

    private BusinessCategoryDTO businessCategory;

    public BusinessProfileCategoryDTO(@NonNull BusinessProfileDTO businessProfile, @NonNull BusinessCategoryDTO businessCategory) {
        this.name = businessCategory.getName();
        this.businessProfile = businessProfile;
        this.businessCategory = businessCategory;
    }
}
