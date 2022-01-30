package lk.dbay.entity.item;

import lk.dbay.entity.DateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ItemPackageItemPackageFeature extends DateTime {

    @EmbeddedId
    private ItemPackageItemPackageFeaturePK itemPackageItemPackageFeatureId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "itemPackageId", referencedColumnName = "itemPackageId", insertable = false, updatable = false, nullable = false)
    private ItemPackage itemPackage;

    @ManyToOne(optional = false)
    @JoinColumn(name = "itemPackageFeatureId", referencedColumnName = "itemPackageFeatureId", insertable = false, updatable = false, nullable = false)
    private ItemPackageFeature itemPackageFeature;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemPackageItemPackageFeature that = (ItemPackageItemPackageFeature) o;
        return Objects.equals(itemPackage.getItemPackageId(), that.itemPackage.getItemPackageId()) &&
                Objects.equals(itemPackageFeature.getName(), that.itemPackageFeature.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemPackage.getItemPackageId(), itemPackageFeature.getName());
    }
}
