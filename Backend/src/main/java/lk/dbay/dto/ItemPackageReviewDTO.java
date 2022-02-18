package lk.dbay.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lk.dbay.entity.*;
import lk.dbay.entity.item.ItemPackageReview;
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
public class ItemPackageReviewDTO extends DateTimeDTO {

    private String itemPackageReviewId;
    private String description;

    //    private ItemDTO item;
    private CustomerProfileDTO customerProfile;
    private String postedDate;
    private int rating;
    private double rating1;
    private int rating2;
    private int tempRating;
    private int likeCount;
    private int dislikeCount;
    private ItemPackageReviewResponseDTO responseByMe;
    private boolean editReview;
    private boolean removeReview;
    private List<ItemPackageReviewDTO> itemPackageReviews;

    public ItemPackageReviewDTO(ItemPackageReview itemPackageReview) {
        if (itemPackageReview != null) {
            this.itemPackageReviewId = itemPackageReview.getItemPackageReviewId();
            this.description = itemPackageReview.getDescription();
            this.rating = itemPackageReview.getRating();
            this.tempRating = itemPackageReview.getRating();
            if (itemPackageReview.getUpdatedAt() != null) {
                this.postedDate = itemPackageReview.getUpdatedAt().format(DateTimeFormatter.ofPattern("MMM dd, yyyy"));
            } else {
                this.postedDate = itemPackageReview.getCreatedAt().format(DateTimeFormatter.ofPattern("MMM dd, yyyy"));
            }
        }
    }

//    public void setItem(ItemReview itemReview) {
//        if (itemReview.getItem() != null) {
//            this.item = new ItemDTO(itemReview.getItem(), false);
//        }
//    }

    public void setCustomerProfile(ItemPackageReview itemPackageReview) {
        if (itemPackageReview.getCustomerProfile() != null) {
            this.customerProfile = new CustomerProfileDTO(itemPackageReview.getCustomerProfile());
        }
    }
}