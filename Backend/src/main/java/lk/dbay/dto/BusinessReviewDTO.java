package lk.dbay.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lk.dbay.entity.BusinessReview;
import lk.dbay.entity.item.ItemPackageReview;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.format.DateTimeFormatter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BusinessReviewDTO extends DateTimeDTO {

    private String businessReviewId;
    private String description;

    //    private ItemDTO item;
    private CustomerProfileDTO customerProfile;
    private String postedDate;

    private int likeCount;
    private int dislikeCount;
    private BusinessReviewResponseDTO responseByMe;

    public BusinessReviewDTO(BusinessReview businessReview) {
        if (businessReview != null) {
            this.businessReviewId = businessReview.getBusinessReviewId();
            this.description = businessReview.getDescription();
            if (businessReview.getUpdatedAt() != null) {
                this.postedDate = businessReview.getUpdatedAt().format(DateTimeFormatter.ofPattern("MMM dd, yyyy"));
            } else {
                this.postedDate = businessReview.getCreatedAt().format(DateTimeFormatter.ofPattern("MMM dd, yyyy"));
            }
        }
    }

//    public void setItem(ItemReview itemReview) {
//        if (itemReview.getItem() != null) {
//            this.item = new ItemDTO(itemReview.getItem(), false);
//        }
//    }

    public void setCustomerProfile(BusinessReview businessReview) {
        if (businessReview.getCustomerProfile() != null) {
            this.customerProfile = new CustomerProfileDTO(businessReview.getCustomerProfile());
        }
    }
}