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
        @UniqueConstraint(columnNames = {"name", "businessProId", "businessCategoryId"}, name = "PackageName")
})
public class ItemPackage extends DateTime {

    @Id
    private String itemPackageId;
    private String name;
    private int quantity;
    private double price;
    private double discountedPrice;
    //    @Lob
    @Column(columnDefinition = "TEXT")
    private String description;
    private double discount;
    private String discountType;
    private boolean confirmed;
    private boolean available;

//    @Lob
//    private byte[] itemPackageImg;
//    private String itemPackageName;
//    private String itemPackageType;

    @ManyToOne(optional = false)
    @JoinColumns({
            @JoinColumn(name = "businessProId", referencedColumnName = "businessProId", updatable = false, nullable = false),
            @JoinColumn(name = "businessCategoryId", referencedColumnName = "businessCategoryId", updatable = false, nullable = false)
    })
    private BusinessProfileCategory businessProfileCategory;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "itemPackage")
    private Set<ItemItemPackage> itemItemPackages;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "itemPackage")
    private Set<ItemPackageItemPackageFeature> itemPackageItemPackageFeatures;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "itemPackage")
    private Set<ItemPackageImg> itemPackageImgs;

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
