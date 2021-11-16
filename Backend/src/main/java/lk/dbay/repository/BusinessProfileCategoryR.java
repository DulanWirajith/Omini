package lk.dbay.repository;

import lk.dbay.entity.BusinessProfileCategory;
import lk.dbay.entity.BusinessProfileCategoryPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessProfileCategoryR extends JpaRepository<BusinessProfileCategory, BusinessProfileCategoryPK> {
}
