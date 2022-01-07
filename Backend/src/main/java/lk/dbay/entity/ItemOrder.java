package lk.dbay.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
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
    private double amount;
    private double discount;
    private int qty;
    private String status;
    private String contactNumber;
    private LocalDateTime receivedTime;

    @ManyToOne(optional = false)
    private CustomerProfile customerProfile;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "itemOrder")
    private Set<OrderDetail> orderDetails;
}
