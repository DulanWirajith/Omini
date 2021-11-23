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
public class ItemItemFeature extends DateTime {

    @EmbeddedId
    private ItemItemFeaturePK itemItemFeatureId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "itemId", referencedColumnName = "itemId", insertable = false, updatable = false, nullable = false)
    private Item item;

    @ManyToOne(optional = false)
    @JoinColumn(name = "itemFeatureId", referencedColumnName = "itemFeatureId", insertable = false, updatable = false, nullable = false)
    private ItemFeature itemFeature;
}
