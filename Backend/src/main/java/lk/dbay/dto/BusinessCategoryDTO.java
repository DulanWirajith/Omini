package lk.dbay.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lk.dbay.entity.BusinessCategory;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BusinessCategoryDTO extends DateTimeDTO {

    private String businessCategoryId;
    private String name;

    public BusinessCategoryDTO(BusinessCategory businessCategory) {
        if (businessCategory != null) {
            this.businessCategoryId = businessCategory.getBusinessCategoryId();
            this.name = businessCategory.getName();
        }
    }
}
