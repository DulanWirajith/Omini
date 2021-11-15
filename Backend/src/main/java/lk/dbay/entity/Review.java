package lk.dbay.entity;

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
public class Review extends DateTime {

    @Id
    private String reviewId;
    private String description;

    @ManyToOne(optional = false)
    private BusinessProfile businessProfile;

    @ManyToOne(optional = false)
    private CustomerProfile customerProfile;
}