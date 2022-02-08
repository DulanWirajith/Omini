package lk.dbay.repository;

import lk.dbay.entity.BusinessReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BusinessReviewR extends JpaRepository<BusinessReview, String> {

    List<BusinessReview> getAllByBusinessProfile_BusinessProId(String businessProId);
}
