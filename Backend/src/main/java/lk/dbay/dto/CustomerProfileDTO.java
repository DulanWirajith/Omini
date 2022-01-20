package lk.dbay.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lk.dbay.entity.CustomerProfile;
import lk.dbay.entity.DbayUser;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CustomerProfileDTO extends DateTimeDTO {

    private String customerProId;
    private String firstName;
    private String lastName;
    private String contactNumber1;
    private String contactNumber2;
    private String customerAddress;
    private String gender;
    private int countPendingOrders;

    private DbayUserDTO dbayUser;
    private TownDTO town;

    public CustomerProfileDTO(CustomerProfile customerProfile) {
        if (customerProfile != null) {
            this.customerProId = customerProfile.getCustomerProId();
            this.firstName = customerProfile.getFirstName();
            this.lastName = customerProfile.getLastName();
            this.contactNumber1 = customerProfile.getContactNumber1();
            this.contactNumber2 = customerProfile.getContactNumber2();
            this.customerAddress = customerProfile.getCustomerAddress();
            this.gender = customerProfile.getGender();
        }
    }

    public void setDbayUser(CustomerProfile customerProfile, boolean needImages) {
        if (customerProfile.getDbayUser() != null) {
            if (needImages) {
                DbayUserDTO dbayUserDTO = new DbayUserDTO(customerProfile.getDbayUser());
                dbayUserDTO.setDbayUserImgs(customerProfile.getDbayUser());
                this.dbayUser = dbayUserDTO;
            } else {
                this.dbayUser = new DbayUserDTO(customerProfile.getDbayUser());
            }
        }
    }

    public void setTown(CustomerProfile customerProfile) {
        if (customerProfile.getTown() != null) {
            this.town = new TownDTO(customerProfile.getTown(), customerProfile.getTown().getDistrict(), customerProfile.getTown().getDistrict().getCountry());
        }
    }
//    public CustomerProfileDTO(@NonNull CustomerProfile customerProfile, @NonNull DbayUserDTO dbayUser) {
//        this(customerProfile);
//        this.dbayUser = dbayUser;
//    }
}
