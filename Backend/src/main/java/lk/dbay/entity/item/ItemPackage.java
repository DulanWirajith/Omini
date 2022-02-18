package lk.dbay.entity.item;

import lk.dbay.entity.BusinessProfileCategory;
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
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name", "businessProId", "businessCategoryId"}, name = "PackageName")
})
public class ItemPackage extends DateTime {

    @Id
    private String itemPackageId;
    private String name;
    private int quantity;
    private boolean makeToOrder;
    private double price;
    private double discountedPrice;
    //    @Lob
    @Column(columnDefinition = "TEXT")
    private String description;
    private double discount;
    private String discountType;
    private boolean confirmed;
    private boolean available;
    private String itemPackageType;
    @Transient
    private boolean favourite;

//    @Lob
//    private byte[] itemPackageImg;
//    private String itemPackageName;
//    private String itemPackageType;

    @ManyToOne(optional = false)
    @JoinColumns({
            @JoinColumn(name = "businessProId", referencedColumnName = "businessProId", updatable = true, nullable = false),
            @JoinColumn(name = "businessCategoryId", referencedColumnName = "businessCategoryId", updatable = true, nullable = false)
    })
    private BusinessProfileCategory businessProfileCategory;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "itemPackage")
    private Set<ItemPackageItemPackageFeature> itemPackageItemPackageFeatures;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "itemPackage")
    private Set<ItemPackageImage> itemPackageImages;

    @OneToOne(cascade = {CascadeType.ALL}, mappedBy = "itemPackage")
    private PackageItem packageItem;

    @OneToOne(cascade = {CascadeType.ALL}, mappedBy = "itemPackage")
    private Item item;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "itemPackage")
    private Set<ItemPackageReview> itemPackageReviews;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemPackage that = (ItemPackage) o;
        return Objects.equals(itemPackageId, that.itemPackageId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemPackageId);
    }
}
