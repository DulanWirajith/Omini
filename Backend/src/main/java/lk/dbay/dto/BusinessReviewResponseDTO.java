package lk.dbay.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lk.dbay.entity.BusinessReviewResponse;
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
public class BusinessReviewResponseDTO extends DateTimeDTO {

    private String businessReviewResponseId;
    //    private String description;
    private String response;
    private BusinessReviewDTO businessReview;
    private CustomerProfileDTO customerProfile;

    public BusinessReviewResponseDTO(BusinessReviewResponse businessReviewResponse) {
        if (businessReviewResponse != null) {
            this.businessReviewResponseId = businessReviewResponse.getBusinessReviewResponseId();
//            this.description = itemReviewResponse.getDescription();
            this.response = businessReviewResponse.getResponse();
        }
    }

    public void setItemPackageReview(BusinessReviewResponse businessReviewResponse) {
        if (businessReviewResponse.getBusinessReview() != null) {
            this.businessReview = new BusinessReviewDTO(businessReviewResponse.getBusinessReview());
        }
    }

    public void setCustomerProfile(ItemPackageReviewResponse itemPackageReviewResponse) {
        if (itemPackageReviewResponse.getCustomerProfile() != null) {
            this.customerProfile = new CustomerProfileDTO(itemPackageReviewResponse.getCustomerProfile());
        }
    }
}