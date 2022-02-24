package lk.dbay.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lk.dbay.entity.Country;
import lk.dbay.entity.Region;
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
    private double latitude;
    private double longitude;
    private RegionDTO region;

    public TownDTO(@NonNull Town town) {
        if (town != null) {
            this.townId = town.getTownId();
            this.latitude = town.getLatitude();
            this.longitude = town.getLongitude();
            if (town.getRegion() != null) {
                this.name = town.getRegion().getName() + " - " + town.getName();
            } else {
                this.name = town.getName();
            }
        }
    }

    public TownDTO(Town town, Region district, Country country) {
        this(town);
        if (district != null && country != null)
            this.region = new RegionDTO(district, country);
    }
}
