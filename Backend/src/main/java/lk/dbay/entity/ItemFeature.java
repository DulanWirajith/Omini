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
        @UniqueConstraint(columnNames = {"name", "business_category_business_category_id"}, name = "Feature_Name")
})
public class ItemFeature extends DateTime {

    @Id
    private String itemFeatureId;
    private String name;
//    @Lob
    @Column(columnDefinition = "TEXT")
    private String description;
    private boolean confirmed;

    @ManyToOne
    private BusinessCategory businessCategory;

}
