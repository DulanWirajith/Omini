package lk.dbay.entity;

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
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"itemTitle", "businessProId", "businessCategoryId"}, name = "ItemTitle")
})
public class Item extends DateTime {

    @Id
    private String itemId;
    private String itemTitle;
    private int itemQty;
    private double itemPrice;
    private String itemDescription;
    private double itemDiscount;
    private String itemDiscountType;
    private boolean itemAvailable;

    @ManyToOne(optional = false)
    @JoinColumns({
            @JoinColumn(name = "businessProId", referencedColumnName = "businessProId", updatable = true, nullable = false),
            @JoinColumn(name = "businessCategoryId", referencedColumnName = "businessCategoryId", updatable = true, nullable = false)
    })
    private BusinessProfileCategory businessProfileCategory;

    @ManyToOne
    private ItemCategory itemCategory;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "item")
    private Set<ItemItemFeature> itemItemFeatures;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "item")
    private Set<ItemImg> itemImgs;

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
