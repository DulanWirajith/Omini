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
//@Table(uniqueConstraints = {
//        @UniqueConstraint(columnNames = {"name", "businessProId", "businessCategoryId"}, name = "ItemTitle")
//})
public class Item extends DateTime {

    @Id
    private String itemId;

    @OneToOne(optional = false)
    private ItemPackage itemPackage;

    @ManyToOne
    private ItemCategory itemCategory;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "item")
    private Set<PackageItemItem> packageItemItems;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(itemId, item.itemId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId);
    }
}
