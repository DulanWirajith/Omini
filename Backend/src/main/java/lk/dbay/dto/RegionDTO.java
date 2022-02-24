package lk.dbay.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lk.dbay.entity.Country;
import lk.dbay.entity.Region;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegionDTO extends DateTimeDTO {

    private String regionId;
    private String name;
    private CountryDTO country;

    public RegionDTO(Region region) {
        if (region != null) {
            this.regionId = region.getRegionId();
            this.name = region.getName();
        }
    }

    public RegionDTO(Region region, Country country) {
        this(region);
        if (country != null) {
            this.country = new CountryDTO(country);
        }
    }
}
