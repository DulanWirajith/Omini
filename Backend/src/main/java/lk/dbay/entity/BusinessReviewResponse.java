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
public class BusinessReviewResponse extends DateTime {

    @Id
    private String businessReviewResponseId;
//    @Lob
//    @Column(columnDefinition = "TEXT")
    private String response;

    @ManyToOne(optional = false)
    private BusinessReview businessReview;

    @ManyToOne(optional = false)
    private CustomerProfile customerProfile;
}
