package lk.dbay.entity.item;

import lk.dbay.entity.CustomerProfile;
import lk.dbay.entity.DateTime;
import lk.dbay.entity.item.Item;
import lk.dbay.entity.item.ItemPackage;
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
public class ItemPackageReview extends DateTime {

    @Id
    private String itemPackageReviewId;
    @Column(columnDefinition = "TEXT")
    private String description;

    private int rating;

//    @ManyToOne
//    private Item item;
    @ManyToOne
    private ItemPackage itemPackage;
    private String reviewType;

    @ManyToOne(optional = false)
    private CustomerProfile customerProfile;

}
