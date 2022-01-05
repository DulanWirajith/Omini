package lk.dbay.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lk.dbay.entity.BusinessArea;
import lk.dbay.entity.Town;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BusinessAreaDTO extends DateTimeDTO {

    private String businessProId;
    private String townId;
    private String name;
    private BusinessProfileDTO businessProfile;
    private TownDTO town;

    public BusinessAreaDTO(BusinessProfileDTO businessProfile, TownDTO town) {
        if (town != null) {
            this.name = town.getName();
            this.town = town;
        }
        if (businessProfile != null) {
            this.businessProfile = businessProfile;
        }
    }
}
