package lk.dbay.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lk.dbay.entity.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Tolerate;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemReviewDTO extends DateTimeDTO {

    private String itemReviewId;
    private String description;

    private ItemDTO item;
    private CustomerProfileDTO customerProfile;
    private String postedDate;

    private int likeCount;
    private int dislikeCount;
    private ItemReviewResponseDTO responseByMe;

    public ItemReviewDTO(ItemReview itemReview) {
        if (itemReview != null) {
            this.itemReviewId = itemReview.getItemReviewId();
            this.description = itemReview.getDescription();
            if (itemReview.getUpdatedAt() != null) {
                this.postedDate = itemReview.getUpdatedAt().format(DateTimeFormatter.ofPattern("MMM dd, yyyy"));
            } else {
                this.postedDate = itemReview.getCreatedAt().format(DateTimeFormatter.ofPattern("MMM dd, yyyy"));
            }
        }
    }

    public void setItem(ItemReview itemReview) {
        if (itemReview.getItem() != null) {
            this.item = new ItemDTO(itemReview.getItem(), false);
        }
    }

    public void setCustomerProfile(ItemReview itemReview) {
        if (itemReview.getCustomerProfile() != null) {
            this.customerProfile = new CustomerProfileDTO(itemReview.getCustomerProfile());
        }
    }
}
