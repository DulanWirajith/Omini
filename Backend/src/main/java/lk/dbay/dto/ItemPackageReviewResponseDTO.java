package lk.dbay.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lk.dbay.entity.item.ItemPackageReviewResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemPackageReviewResponseDTO extends DateTimeDTO {

    private String itemReviewResponseId;
    //    private String description;
    private String response;
    private ItemPackageReviewDTO itemPackageReview;
    private CustomerProfileDTO customerProfile;

    public ItemPackageReviewResponseDTO(ItemPackageReviewResponse itemPackageReviewResponse) {
        if (itemPackageReviewResponse != null) {
            this.itemReviewResponseId = itemPackageReviewResponse.getItemReviewResponseId();
//            this.description = itemReviewResponse.getDescription();
            this.response = itemPackageReviewResponse.getResponse();
        }
    }

    public void setItemPackageReview(ItemPackageReviewResponse itemPackageReviewResponse) {
        if (itemPackageReviewResponse.getItemPackageReview() != null) {
            this.itemPackageReview = new ItemPackageReviewDTO(itemPackageReviewResponse.getItemPackageReview());
        }
    }

    public void setCustomerProfile(ItemPackageReviewResponse itemPackageReviewResponse) {
        if (itemPackageReviewResponse.getCustomerProfile() != null) {
            this.customerProfile = new CustomerProfileDTO(itemPackageReviewResponse.getCustomerProfile());
        }
    }
}