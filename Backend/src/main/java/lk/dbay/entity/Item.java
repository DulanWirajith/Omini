package lk.dbay.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
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

    @Lob
    private byte[] itemImg;
    private String itemImgName;
    private String itemImgType;

    @ManyToOne(optional = false)
    @JoinColumns({
            @JoinColumn(name = "businessProId", referencedColumnName = "businessProId", updatable = false, nullable = false),
            @JoinColumn(name = "businessCategoryId", referencedColumnName = "businessCategoryId", updatable = false, nullable = false)
    })
    private BusinessProfileCategory businessProfileCategory;

    @ManyToOne
    private ItemCategory itemCategory;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "item")
    private Set<ItemItemFeature> itemItemFeatures;

}
