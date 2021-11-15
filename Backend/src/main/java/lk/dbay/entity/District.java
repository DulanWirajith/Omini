package lk.dbay.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = "name", name = "District_Name")
})
public class District extends DateTime {

    @Id
    private String districtId;
    private String name;

    @ManyToOne(optional = false)
    private Country country;

}
