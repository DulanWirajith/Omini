package lk.dbay.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;

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

    @OneToOne(optional = false)
    private CustomerProfile customerProfile;
}
