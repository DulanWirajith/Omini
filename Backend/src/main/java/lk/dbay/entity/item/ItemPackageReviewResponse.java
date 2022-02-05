package lk.dbay.entity.item;

import lk.dbay.entity.CustomerProfile;
import lk.dbay.entity.DateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ItemPackageReviewResponse extends DateTime {

    @Id
    private String itemPackageReviewResponseId;
//    @Column(columnDefinition = "TEXT")
//    private String description;

    private String response;

    @ManyToOne(optional = false)
    private ItemPackageReview itemPackageReview;

    @ManyToOne(optional = false)
    private CustomerProfile customerProfile;

}
