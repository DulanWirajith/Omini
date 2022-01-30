package lk.dbay.entity.item;

import lk.dbay.entity.DateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PackageItemItem extends DateTime {

    @EmbeddedId
    private PackageItemItemPK packageItemItemId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "itemId", referencedColumnName = "itemId", insertable = false, updatable = false, nullable = false)
    private Item item;

    @ManyToOne(optional = false)
    @JoinColumn(name = "packageItemId", referencedColumnName = "packageItemId", insertable = false, updatable = false, nullable = false)
    private PackageItem packageItem;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PackageItemItem that = (PackageItemItem) o;
        return Objects.equals(item.getItemId(), that.item.getItemId()) &&
                Objects.equals(packageItem.getPackageItemId(), that.packageItem.getPackageItemId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(item.getItemId(), packageItem.getPackageItemId());
    }
}
