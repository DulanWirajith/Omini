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
public class ItemItemPackage extends DateTime {

    @EmbeddedId
    private ItemItemPackagePK itemItemPackageId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "itemId", referencedColumnName = "itemId", insertable = false, updatable = false, nullable = false)
    private Item item;

    @ManyToOne(optional = false)
    @JoinColumn(name = "itemPackageId", referencedColumnName = "itemPackageId", insertable = false, updatable = false, nullable = false)
    private ItemPackage itemPackage;
}
