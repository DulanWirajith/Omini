package lk.dbay.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lk.dbay.entity.CustomerProfile;
import lk.dbay.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CustomerItemFavouriteDTO extends DateTimeDTO {

    //    private String customerItemFavouriteId;
    private CustomerProfileDTO customerProfile;
    private ItemDTO item;

    public CustomerItemFavouriteDTO(CustomerProfile customerProfile, Item item) {
        if (customerProfile != null) {
            this.customerProfile = new CustomerProfileDTO(customerProfile);
        }
        if (item != null) {
            this.item = new ItemDTO(item, false);
        }
    }
}
