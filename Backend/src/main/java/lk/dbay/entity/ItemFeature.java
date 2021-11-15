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
        @UniqueConstraint(columnNames = "name", name = "Feature_Name")
})
public class ItemFeature extends DateTime {

    @Id
    private String featureId;
    private String name;

    @ManyToOne(optional = false)
    private Item item;
}