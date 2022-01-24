package lk.dbay.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ItemReviewResponse extends DateTime {

    @Id
    private String itemReviewResponseId;
//    @Column(columnDefinition = "TEXT")
//    private String description;

    private String response;

    @ManyToOne(optional = false)
    private ItemReview itemReview;

    @ManyToOne(optional = false)
    private CustomerProfile customerProfile;

}
