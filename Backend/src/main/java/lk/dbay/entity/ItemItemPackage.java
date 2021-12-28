package lk.dbay.entity;

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
public class ItemItemPackage extends DateTime {

    @EmbeddedId
    private ItemItemPackagePK itemItemPackageId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "itemId", referencedColumnName = "itemId", insertable = false, updatable = false, nullable = false)
    private Item item;

    @ManyToOne(optional = false)
    @JoinColumn(name = "itemPackageId", referencedColumnName = "itemPackageId", insertable = false, updatable = false, nullable = false)
    private ItemPackage itemPackage;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemItemPackage that = (ItemItemPackage) o;
        return Objects.equals(item.getItemId(), that.item.getItemId()) &&
                Objects.equals(itemPackage.getItemPackageId(), that.itemPackage.getItemPackageId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(item.getItemId(), itemPackage.getItemPackageId());
    }
}
