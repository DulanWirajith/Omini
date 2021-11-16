package lk.dbay.repository;

import lk.dbay.entity.BusinessCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessCategoryR extends JpaRepository<BusinessCategory,String> {
}
