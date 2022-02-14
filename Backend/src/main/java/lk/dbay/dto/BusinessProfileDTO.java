package lk.dbay.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lk.dbay.entity.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BusinessProfileDTO extends DateTimeDTO {

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
    private boolean followed;
    private int proViewCount;
    private int contactViewCount;
    private int countPendingOrders;
    private BusinessCategoryDTO defaultBusiness;
    private DbayUserDTO dbayUser;
    private TownDTO town;
    private List<BusinessAreaDTO> businessAreas;
    private List<BusinessProfileCategoryDTO> businessProfileCategories;
    private ItemPackageDTO itemPackage;
    private List<ItemCategoryDTO> itemCategories;

    public BusinessProfileDTO(BusinessProfile businessProfile) {
        if (businessProfile != null) {
            this.businessProId = businessProfile.getBusinessProId();
            this.businessName = businessProfile.getBusinessName();
            this.businessEmail = businessProfile.getBusinessEmail();
            this.businessAddress = businessProfile.getBusinessAddress();
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
            this.defaultBusiness = new BusinessCategoryDTO(businessProfile.getDefaultBusiness());
        }
    }

    public void setDbayUser(BusinessProfile businessProfile) {
        if (businessProfile.getDbayUser() != null) {
//            if (needImages) {
//                DbayUserDTO dbayUserDTO = new DbayUserDTO(businessProfile.getDbayUser());
//                dbayUserDTO.setDbayUserImgs(businessProfile.getDbayUser());
//                this.dbayUser = dbayUserDTO;
//            } else {
                this.dbayUser = new DbayUserDTO(businessProfile.getDbayUser());
//            }
        }
    }

    public void setTown(BusinessProfile businessProfile) {
        if (businessProfile.getTown() != null) {
            this.town = new TownDTO(businessProfile.getTown(), businessProfile.getTown().getDistrict(), businessProfile.getTown().getDistrict().getCountry());
        }
    }

    public void setBusinessAreas(BusinessProfile businessProfile) {
        businessAreas = new ArrayList<>();
        for (BusinessArea businessArea : businessProfile.getBusinessAreas()) {
            businessAreas.add(new BusinessAreaDTO(new BusinessProfileDTO(businessProfile), new TownDTO(businessArea.getTown())));
        }
    }

    public void setBusinessProfileCategories(BusinessProfile businessProfile) {
        businessProfileCategories = new ArrayList<>();
        for (BusinessProfileCategory businessProfileCategory : businessProfile.getBusinessProfileCategories()) {
            businessProfileCategories.add(new BusinessProfileCategoryDTO(businessProfile, businessProfileCategory.getBusinessCategory()));
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BusinessProfileDTO that = (BusinessProfileDTO) o;
        return Objects.equals(businessName, that.businessName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(businessName);
    }

//    public BusinessProfileDTO(BusinessProfile businessProfile, DbayUser dbayUser) {
//        this(businessProfile);
//        if (dbayUser != null) {
//            this.dbayUser = new DbayUserDTO(dbayUser);
//        }
//    }

//    public BusinessProfileDTO(BusinessProfile businessProfile, DbayUser dbayUser, boolean needAreas, boolean needCategories, boolean needImages) {
////        this(businessProfile);
////        if (dbayUser != null) {
////            if (needImages) {
////                this.dbayUser = new DbayUserDTO(dbayUser, dbayUser.getDbayUserImgs());
////            } else {
////                this.dbayUser = new DbayUserDTO(dbayUser);
////            }
////        }
//        if (businessProfile != null) {
////            this.town = new TownDTO(businessProfile.getTown(), businessProfile.getTown().getDistrict(), businessProfile.getTown().getDistrict().getCountry());
//            if (needAreas) {
////                businessAreas = new ArrayList<>();
////                for (BusinessArea businessArea : businessProfile.getBusinessAreas()) {
////                    businessAreas.add(new BusinessAreaDTO(new BusinessProfileDTO(businessProfile), new TownDTO(businessArea.getTown())));
////                }
//            }
//            if (needCategories) {
////                businessProfileCategories = new ArrayList<>();
////                for (BusinessProfileCategory businessProfileCategory : businessProfile.getBusinessProfileCategories()) {
////                    businessProfileCategories.add(new BusinessProfileCategoryDTO(businessProfile, businessProfileCategory.getBusinessCategory()));
////                }
//            }
//        }
//    }

//    public BusinessProfileDTO(@NonNull BusinessProfile businessProfile, @NonNull DbayUserDTO user, @NonNull List<BusinessAreaDTO> businessAreas, @NonNull List<BusinessProfileCategoryDTO> businessProfileCategories) {
//        this(businessProfile);
//        this.user = user;
//        this.businessAreas = businessAreas;
//        this.businessProfileCategories = businessProfileCategories;
//    }
}
