package lk.dbay.repository;

import lk.dbay.entity.BusinessReviewResponse;
import lk.dbay.entity.item.ItemPackageReviewResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BusinessReviewResponseR extends JpaRepository<BusinessReviewResponse,String> {

    List<BusinessReviewResponse> getAllByBusinessReview_BusinessReviewId(String businessReviewId);
}
