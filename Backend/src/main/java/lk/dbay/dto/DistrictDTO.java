package lk.dbay.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lk.dbay.entity.District;
import lk.dbay.entity.Town;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DistrictDTO extends DateTimeDTO {

    private String districtId;
    private String name;

    public DistrictDTO(@NonNull District district) {
        this.districtId = district.getDistrictId();
        this.name = district.getName();
    }
}
