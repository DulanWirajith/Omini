package lk.dbay.repository;

import lk.dbay.entity.CustomerProfile;
import lk.dbay.entity.ItemReviewResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemReviewResponseR extends JpaRepository<ItemReviewResponse, String> {

    List<ItemReviewResponse> getAllByItemReview_ItemReviewId(String itemReviewId);

}
