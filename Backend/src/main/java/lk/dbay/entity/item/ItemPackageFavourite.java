package lk.dbay.entity.item;

import lk.dbay.entity.CustomerProfile;
import lk.dbay.entity.DateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ItemPackageFavourite extends DateTime {

    @EmbeddedId
    private ItemPackageFavouritePK itemPackageFavouriteId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "customerProId", referencedColumnName = "customerProId", insertable = false, updatable = false, nullable = false)
    private CustomerProfile customerProfile;

    @ManyToOne(optional = false)
    @JoinColumn(name = "itemPackageId", referencedColumnName = "itemPackageId", insertable = false, updatable = false, nullable = false)
    private ItemPackage itemPackage;
}
