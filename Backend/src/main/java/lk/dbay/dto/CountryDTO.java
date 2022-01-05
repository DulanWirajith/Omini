package lk.dbay.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lk.dbay.entity.Country;
import lk.dbay.entity.Town;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CountryDTO extends DateTimeDTO {

    private String countryId;
    private String name;

    public CountryDTO(Country country) {
        if (country != null) {
            this.countryId = country.getCountryId();
            this.name = country.getName();
        }
    }
}
