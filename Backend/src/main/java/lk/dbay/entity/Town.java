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
        @UniqueConstraint(columnNames = "name", name = "Town_Name")
})
public class Town extends DateTime {

    @Id
    private String townId;
    private String name;
    private double latitude;
    private double longitude;

    @ManyToOne(optional = false)
    private Region region;
}
