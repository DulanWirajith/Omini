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

    public BusinessAreaDTO(@NonNull BusinessProfileDTO businessProfile, @NonNull TownDTO town) {
        this.name = town.getName();
        this.businessProfile = businessProfile;
        this.town = town;
    }
}
