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
public class TownDTO extends DateTimeDTO {

    private String townId;
    private String name;
    private DistrictDTO district;

    public TownDTO(@NonNull Town town) {
        this.townId = town.getTownId();
        if (town.getDistrict() != null) {
            this.name = town.getDistrict().getName() + "-" + town.getName();
        } else {
            this.name = town.getName();
        }
    }

    public TownDTO(@NonNull Town town, @NonNull District district, @NonNull Country country) {
        this(town);
        this.district = new DistrictDTO(district, country);
    }
}
