package lk.dbay.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lk.dbay.entity.BusinessCategory;
import lk.dbay.entity.BusinessProfile;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BusinessProfileDTO extends DateTimeDTO {

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
    private int proViewCount;
    private int contactViewCount;
    private DbayUserDTO user;

    public BusinessProfileDTO(@NonNull BusinessProfile businessProfile) {
        this.businessProId = businessProfile.getBusinessProId();
        this.businessName = businessProfile.getBusinessName();
        this.address = businessProfile.getAddress();
        this.contactNumber1 = businessProfile.getContactNumber1();
        this.contactNumber2 = businessProfile.getContactNumber2();
        this.contactNumber3 = businessProfile.getContactNumber3();
        this.businessRegistrationCode = businessProfile.getBusinessRegistrationCode();
        this.businessOwner = businessProfile.getBusinessOwner();
        this.businessOwnerNic = businessProfile.getBusinessOwnerNic();
        this.socialFb = businessProfile.getSocialFb();
        this.socialInsta = businessProfile.getSocialInsta();
        this.socialTwitter = businessProfile.getSocialTwitter();
        this.socialLinkedIn = businessProfile.getSocialLinkedIn();
        this.proViewCount = businessProfile.getProViewCount();
        this.contactViewCount = businessProfile.getContactViewCount();
    }

    public BusinessProfileDTO(@NonNull BusinessProfile businessProfile, @NonNull DbayUserDTO user) {
        this(businessProfile);
        this.user = user;
    }
}
