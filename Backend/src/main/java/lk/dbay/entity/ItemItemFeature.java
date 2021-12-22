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
public class ItemItemFeature extends DateTime {

    @EmbeddedId
    private ItemItemFeaturePK itemItemFeatureId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "itemId", referencedColumnName = "itemId", insertable = false, updatable = false, nullable = false)
    private Item item;

    @ManyToOne(optional = false)
    @JoinColumn(name = "itemFeatureId", referencedColumnName = "itemFeatureId", insertable = false, updatable = false, nullable = false)
    private ItemFeature itemFeature;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemItemFeature that = (ItemItemFeature) o;
        return Objects.equals(item.getItemId(), that.item.getItemId()) &&
                Objects.equals(itemFeature.getName(), that.itemFeature.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(item.getItemId(), itemFeature.getName());
    }
}
