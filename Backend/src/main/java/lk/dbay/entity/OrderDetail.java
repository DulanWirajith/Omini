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
public class OrderDetail extends DateTime {

    @Id
    private String orderDetailId;
    private int quantity;
    private double price;
    private double discount;

    @ManyToOne(optional = false)
    private ItemOrder itemOrder;

    @ManyToOne(optional = false)
    @JoinColumns({
            @JoinColumn(name = "itemPId", referencedColumnName = "itemId", updatable = false, nullable = false),
            @JoinColumn(name = "itemPackageId", referencedColumnName = "itemPackageId", updatable = false, nullable = false)
    })
    private ItemItemPackage itemItemPackage;

    @ManyToOne(optional = false)
    @JoinColumns({
            @JoinColumn(name = "itemCId", referencedColumnName = "itemId", updatable = false, nullable = false),
            @JoinColumn(name = "itemCategoryId", referencedColumnName = "itemCategoryId", updatable = false, nullable = false)
    })
    private ItemItemCategory itemItemCategory;
}
