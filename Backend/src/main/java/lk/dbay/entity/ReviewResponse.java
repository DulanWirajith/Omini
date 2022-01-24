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
public class ReviewResponse extends DateTime {

    @Id
    private String responseId;
//    @Lob
    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne(optional = false)
    private BusinessReview review;

    @ManyToOne(optional = false)
    private CustomerProfile customerProfile;
}
