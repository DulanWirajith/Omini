package lk.dbay.repository;

import lk.dbay.entity.BusinessArea;
import lk.dbay.entity.BusinessAreaPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessAreaR extends JpaRepository<BusinessArea, BusinessAreaPK> {
}
