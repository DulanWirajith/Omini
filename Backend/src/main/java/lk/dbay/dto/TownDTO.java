package lk.dbay.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
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

    public TownDTO(@NonNull Town town) {
        this.townId = town.getTownId();
        this.name = town.getName();
    }
}
