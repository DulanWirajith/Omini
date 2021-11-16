package lk.dbay.repository;

import lk.dbay.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewR extends JpaRepository<Review,String> {
}
