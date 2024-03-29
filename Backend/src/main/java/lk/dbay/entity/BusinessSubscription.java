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
public class BusinessSubscription extends DateTime {

    @EmbeddedId
    private BusinessSubscriptionPK businessSubscriptionId;
    private boolean paid;
    private double paidAmount;

    @ManyToOne(optional = false)
    @JoinColumn(name = "businessProId", referencedColumnName = "businessProId", insertable = false, updatable = false, nullable = false)
    private BusinessProfile businessProfile;

    @ManyToOne(optional = false)
    @JoinColumn(name = "subscriptionId", referencedColumnName = "subscriptionId", insertable = false, updatable = false, nullable = false)
    private Subscription subscription;
}
