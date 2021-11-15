package lk.dbay.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = "title", name = "SubscriptionTitle")
})
public class Subscription extends DateTime {

    @Id
    private String subscriptionId;
    private String title;
    private String duration;
    private double durationVal;
    private double amount;
    private double offer;
}
