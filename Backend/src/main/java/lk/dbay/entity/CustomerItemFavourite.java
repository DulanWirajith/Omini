package lk.dbay.entity;

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
public class CustomerItemFavourite extends DateTime {

    @EmbeddedId
    private CustomerItemFavouritePK customerItemFavouriteId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "customerProId", referencedColumnName = "customerProId", insertable = false, updatable = false, nullable = false)
    private CustomerProfile customerProfile;

    @ManyToOne(optional = false)
    @JoinColumn(name = "itemId", referencedColumnName = "itemId", insertable = false, updatable = false, nullable = false)
    private Item item;
}
