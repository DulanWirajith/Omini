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
        @UniqueConstraint(columnNames = {"name", "businessProId", "businessCategoryId"}, name = "PackageName")
})
public class ItemPackage extends DateTime {

    @Id
    private String itemPackageId;
    private String name;
    private String description;
    private double price;
    private double discount;
    private String discountType;

    @Lob
    private byte[] itemPackageImg;
    private String itemPackageName;
    private String itemPackageType;

    @ManyToOne(optional = false)
    @JoinColumns({
            @JoinColumn(name = "businessProId", referencedColumnName = "businessProId", updatable = false, nullable = false),
            @JoinColumn(name = "businessCategoryId", referencedColumnName = "businessCategoryId", updatable = false, nullable = false)
    })
    private BusinessProfileCategory businessProfileCategory;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "itemPackage")
    private Set<ItemItemPackage> itemItemPackages;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "itemPackage")
    private Set<ItemPackageImg> itemPackageImgs;
}
