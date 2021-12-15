package lk.dbay.repository;

import lk.dbay.entity.BusinessArea;
import lk.dbay.entity.BusinessAreaPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface BusinessAreaR extends JpaRepository<BusinessArea, BusinessAreaPK> {

    @Modifying
    @Transactional
    @Query(value = "delete from BusinessArea where businessAreaId.businessProId=?1")
    void deleteBusinessAreas(String businessProfileId);

}
