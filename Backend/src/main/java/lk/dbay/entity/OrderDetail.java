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
    private boolean makeToOrder;
    private double price;
    @Transient
    private double discountedPrice;
    private double discount;

    @ManyToOne(optional = false)
    private ItemOrder itemOrder;

    private String orderDetailType;
    private String status;
    private boolean available;

    @ManyToOne(optional = false)
    @JoinColumns({
            @JoinColumn(name = "businessProId", referencedColumnName = "businessProId", updatable = true, nullable = false),
            @JoinColumn(name = "businessCategoryId", referencedColumnName = "businessCategoryId", updatable = true, nullable = false)
    })
    private BusinessProfileCategory businessProfileCategory;

//    @ManyToOne
//    @JoinColumns({
//            @JoinColumn(name = "itemId", referencedColumnName = "itemId", updatable = false, nullable = false),
//            @JoinColumn(name = "itemPackageId", referencedColumnName = "itemPackageId", updatable = false, nullable = false)
//    })
//    private ItemItemPackage itemItemPackage;

    @ManyToOne
    private ItemPackage itemPackage;

    @ManyToOne
    private Item item;


//    @ManyToOne
//    private ItemCategory itemCategory;
}
