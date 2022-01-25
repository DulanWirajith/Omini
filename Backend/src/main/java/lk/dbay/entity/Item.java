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
        @UniqueConstraint(columnNames = {"name", "businessProId", "businessCategoryId"}, name = "ItemTitle")
})
public class Item extends DateTime {

    @Id
    private String itemId;
    private String name;
    private int quantity;
    private boolean makeToOrder;
    private double price;
    @Transient
    private double discountedPrice;
    //    @Lob
    @Column(columnDefinition = "TEXT")
    private String description;
    private double discount;
    private String discountType;
    private boolean confirmed;
    private boolean available;

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

//    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "item")
//    private Set<ItemReview> itemReviews;

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
