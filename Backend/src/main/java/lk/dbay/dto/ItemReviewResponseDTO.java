package lk.dbay.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lk.dbay.entity.ItemReview;
import lk.dbay.entity.ItemReviewResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemReviewResponseDTO extends DateTimeDTO {

    private String itemReviewResponseId;
    //    private String description;
    private String response;
    private ItemReviewDTO itemReview;
    private CustomerProfileDTO customerProfile;

    public ItemReviewResponseDTO(ItemReviewResponse itemReviewResponse) {
        if (itemReviewResponse != null) {
            this.itemReviewResponseId = itemReviewResponse.getItemReviewResponseId();
//            this.description = itemReviewResponse.getDescription();
            this.response = itemReviewResponse.getResponse();
        }
    }

    public void setItemReview(ItemReviewResponse itemReviewResponse) {
        if (itemReviewResponse.getItemReview() != null) {
            this.itemReview = new ItemReviewDTO(itemReviewResponse.getItemReview());
        }
    }

    public void setCustomerProfile(ItemReviewResponse itemReviewResponse) {
        if (itemReviewResponse.getCustomerProfile() != null) {
            this.customerProfile = new CustomerProfileDTO(itemReviewResponse.getCustomerProfile());
        }
    }
}
