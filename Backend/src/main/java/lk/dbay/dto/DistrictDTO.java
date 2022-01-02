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

    public DistrictDTO(@NonNull District district) {
        this.districtId = district.getDistrictId();
        this.name = district.getName();
    }

    public DistrictDTO(@NonNull District district, @NonNull Country country) {
        this(district);
        this.country = new CountryDTO(country);
    }
}
