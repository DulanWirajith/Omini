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

    @Query(value = "select distinct i from ItemPackage i inner join i.itemPackageItemPackageFeatures iif where (i.name like ?1 or iif.itemPackageFeature.name like ?1) and (i.businessProfileCategory.businessCategory.businessCategoryId=?2 or iif.itemPackage.businessProfileCategory.businessCategory.businessCategoryId=?2)")
    List<ItemPackage> getItemPackagesBySearch(String txt, String category);

    @Query(value = "select distinct i from ItemPackage i inner join i.itemPackageItemPackageFeatures iif inner join i.itemItemPackages iip where i.name like ?1 or iip.item.name like ?1 or iif.itemPackageFeature.name like ?1")
    List<ItemPackage> getItemPackagesBySearch(String txt);
}
