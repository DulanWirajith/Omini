package lk.dbay.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ItemOrder extends DateTime {

    @Id
    private String orderId;
    private LocalDateTime orderDate;
//    private double amount;
//    private double discount;
//    private int qty;
    private String status;
    private String contactNumber;
    private LocalDateTime receivedTime;

//    @ManyToOne(optional = false)
//    @JoinColumns({
//            @JoinColumn(name = "businessProId", referencedColumnName = "businessProId", updatable = false, nullable = false),
//            @JoinColumn(name = "businessCategoryId", referencedColumnName = "businessCategoryId", updatable = false, nullable = false)
//    })
//    private BusinessProfileCategory businessProfileCategory;

    @ManyToOne(optional = false)
    private CustomerProfile customerProfile;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "itemOrder")
    private Set<OrderDetail> orderDetails;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemOrder itemOrder = (ItemOrder) o;
        return Objects.equals(orderId, itemOrder.orderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId);
    }
}
