package lk.dbay.repository;

import lk.dbay.entity.BusinessProfileCategoryPK;
import lk.dbay.entity.Item;
import lk.dbay.entity.ItemPackage;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemPackageR extends JpaRepository<ItemPackage,String> {

    @Query(value = "from ItemPackage where businessProfileCategory.businessProfileCategoryId=?1 order by name")
    List<ItemPackage> getItemPackagesOrdered(BusinessProfileCategoryPK businessCategoryId);
}
