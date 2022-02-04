package lk.dbay.repository;

import lk.dbay.entity.item.ItemPackageReviewResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemPackageReviewResponseR extends JpaRepository<ItemPackageReviewResponse, String> {

    List<ItemPackageReviewResponse> getAllByItemPackageReview_ItemPackageReviewId(String itemPackageReviewId);
}
