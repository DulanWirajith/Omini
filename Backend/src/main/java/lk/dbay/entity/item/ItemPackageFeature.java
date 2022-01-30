package lk.dbay.entity.item;

import lk.dbay.entity.BusinessCategory;
import lk.dbay.entity.DateTime;
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
        @UniqueConstraint(columnNames = {"name", "business_category_business_category_id"}, name = "Package_Feature_Name")
})
public class ItemPackageFeature extends DateTime {

    @Id
    private String itemPackageFeatureId;
    private String name;
    //    @Lob
    @Column(columnDefinition = "TEXT")
    private String description;
    private boolean confirmed;

    @ManyToOne
    private BusinessCategory businessCategory;
}
