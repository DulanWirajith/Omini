package lk.dbay.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lk.dbay.entity.CustomerProfile;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CustomerProfileDTO extends DateTimeDTO {

    private String customerProId;
    private String customerName;
    private String contactNumber;
    private String customerAddress;
    private String gender;

    private DbayUserDTO user;
    private TownDTO town;

    public CustomerProfileDTO(@NonNull CustomerProfile customerProfile) {
        this.customerProId = customerProfile.getCustomerProId();
        this.customerName = customerProfile.getCustomerName();
        this.contactNumber = customerProfile.getContactNumber();
        this.customerAddress = customerProfile.getCustomerAddress();
        this.gender = customerProfile.getGender();
    }

    public CustomerProfileDTO(@NonNull CustomerProfile customerProfile, @NonNull DbayUserDTO user) {
        this(customerProfile);
        this.user = user;
    }
}
