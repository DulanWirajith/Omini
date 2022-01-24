package lk.dbay.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ItemReview extends DateTime {

    @Id
    private String itemReviewId;
    @Column(columnDefinition = "TEXT")
    private String description;

    private int rating;

    @ManyToOne(optional = false)
    private Item item;

    @ManyToOne(optional = false)
    private CustomerProfile customerProfile;

}
