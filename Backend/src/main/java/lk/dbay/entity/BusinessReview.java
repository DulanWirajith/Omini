package lk.dbay.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BusinessReview extends DateTime {

    @Id
    private String businessReviewId;
    //    @Lob
    @Column(columnDefinition = "TEXT")
    private String description;

    private int rating;

    @ManyToOne(optional = false)
    private BusinessProfile businessProfile;

    @ManyToOne(optional = false)
    private CustomerProfile customerProfile;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "businessReview")
    private Set<BusinessReviewResponse> businessReviewResponses;
}
