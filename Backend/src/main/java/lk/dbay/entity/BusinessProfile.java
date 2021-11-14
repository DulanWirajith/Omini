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
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"contactNumber1", "contactNumber2", "contactNumber3"}, name = "ContactNumber"),
        @UniqueConstraint(columnNames = "address", name = "Address"),
        @UniqueConstraint(columnNames = "businessRegistrationCode", name = "BusinessRegistrationCode"),
        @UniqueConstraint(columnNames = "businessOwnerNic", name = "BusinessOwnerNic"),
        @UniqueConstraint(columnNames = "socialFb", name = "SocialFb"),
        @UniqueConstraint(columnNames = "socialInsta", name = "SocialInsta"),
        @UniqueConstraint(columnNames = "socialLinkedIn", name = "SocialLinkedIn"),
})
public class BusinessProfile {

    @Id
    private String businessProId;
    private String businessName;
    private String address;
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

    @OneToOne(optional = false)
    private Town town;
}
