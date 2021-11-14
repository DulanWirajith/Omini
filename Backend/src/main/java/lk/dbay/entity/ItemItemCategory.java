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
public class ItemItemCategory extends DateTime {

    @EmbeddedId
    private ItemItemCategoryPK itemItemCategoryId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "itemId", referencedColumnName = "itemId", insertable = false, updatable = false, nullable = false)
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "itemCategoryId", referencedColumnName = "itemCategoryId", insertable = false, updatable = false, nullable = false)
    private ItemCategory itemCategory;
}
