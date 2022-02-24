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
        @UniqueConstraint(columnNames = "name", name = "Region_Name")
})
public class Region extends DateTime {

    @Id
    private String regionId;
    private String name;

    @ManyToOne(optional = false)
    private Country country;

}
