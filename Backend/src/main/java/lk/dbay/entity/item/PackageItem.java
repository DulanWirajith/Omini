package lk.dbay.entity.item;


import lk.dbay.entity.DateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PackageItem extends DateTime {

    @Id
    private String packageItemId;

    @OneToOne
    private ItemPackage itemPackage;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "packageItem")
    private Set<PackageItemItem> packageItemItems;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PackageItem itemsPackage = (PackageItem) o;
        return Objects.equals(packageItemId, itemsPackage.packageItemId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(packageItemId);
    }
}
