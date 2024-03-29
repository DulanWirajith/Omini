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
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"contactNumber1", "contactNumber2", "contactNumber3"}, name = "ContactNumbers"),
        @UniqueConstraint(columnNames = "businessAddress", name = "BusinessAddress"),
        @UniqueConstraint(columnNames = "businessRegistrationCode", name = "BusinessRegistrationCode"),
        @UniqueConstraint(columnNames = "businessOwnerNic", name = "BusinessOwnerNic"),
//        @UniqueConstraint(columnNames = "socialFb", name = "SocialFb"),
//        @UniqueConstraint(columnNames = "socialInsta", name = "SocialInsta"),
//        @UniqueConstraint(columnNames = "socialLinkedIn", name = "SocialLinkedIn"),
})
public class BusinessProfile extends DateTime {

    @Id
    private String businessProId;
    private String businessName;
    private String businessEmail;
    private String businessAddress;
    private String contactNumber1;
    private String contactNumber2;
    private String contactNumber3;
    private String businessRegistrationCode;
    private String businessOwner;
    private String businessOwnerNic;
    private String socialFb;
    private String socialInsta;
    private String socialTwitter;
    private String socialLinkedIn;
    private int proViewCount;
    private int contactViewCount;

    @ManyToOne(optional = false)
    private BusinessCategory defaultBusiness;

    @ManyToOne(optional = false)
    private Town town;

    @OneToOne(optional = false)
    private DbayUser dbayUser;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "businessProfile")
    private Set<BusinessArea> businessAreas;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "businessProfile")
    private Set<BusinessProfileCategory> businessProfileCategories;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "businessProfile")
    private Set<BusinessReview> businessReviews;
}
