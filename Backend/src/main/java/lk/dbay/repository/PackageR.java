package lk.dbay.repository;

import lk.dbay.entity.BusinessProfileCategoryPK;
import lk.dbay.entity.item.PackageItem;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PackageR extends JpaRepository<PackageItem, String> {

    @Query(value = "from PackageItem where itemPackage.businessProfileCategory.businessProfileCategoryId=?1 order by itemPackage.name")
    List<PackageItem> getPackageItemsOrdered(BusinessProfileCategoryPK businessCategoryId, Pageable pageable);
}
