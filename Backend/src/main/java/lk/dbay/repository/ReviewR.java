package lk.dbay.repository;

import lk.dbay.entity.BusinessReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewR extends JpaRepository<BusinessReview,String> {
}
