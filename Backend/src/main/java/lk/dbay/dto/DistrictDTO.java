package lk.dbay.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lk.dbay.entity.Country;
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
    private CountryDTO country;

    public DistrictDTO(District district) {
        if (district != null) {
            this.districtId = district.getDistrictId();
            this.name = district.getName();
        }
    }

    public DistrictDTO(District district, Country country) {
        this(district);
        if (country != null) {
            this.country = new CountryDTO(country);
        }
    }
}
