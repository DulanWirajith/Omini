package lk.dbay.repository;

import lk.dbay.entity.BusinessProfileCategoryPK;
import lk.dbay.entity.item.ItemPackage;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemPackageR extends JpaRepository<ItemPackage, String> {

    @Query(value = "from ItemPackage where businessProfileCategory.businessProfileCategoryId=?1 order by name")
    List<ItemPackage> getItemsPackageOrdered(BusinessProfileCategoryPK businessCategoryId, Pageable pageable);

    @Query(value = "from ItemPackage where businessProfileCategory.businessProfile.businessProId=?1")
    List<ItemPackage> getItemsForBusinessProId(String businessProId);

    @Query(value = "" +
            "select distinct i from ItemPackage i inner join i.itemPackageItemPackageFeatures iif inner join i.packageItem.packageItemItems pii " +
            "where i.available=true and (i.name like ?1 or iif.itemPackageFeature.name like ?1 or pii.item.itemPackage.name like ?1) and (i.businessProfileCategory.businessCategory.businessCategoryId=?2 or iif.itemPackage.businessProfileCategory.businessCategory.businessCategoryId=?2)")
    List<ItemPackage> getItemPackagesBySearchPackage(String txt, String category);

    @Query(value = "" +
            "select distinct i from ItemPackage i inner join i.itemPackageItemPackageFeatures iif " +
            "where i.available=true and (i.name like ?1 or iif.itemPackageFeature.name like ?1) and (i.businessProfileCategory.businessCategory.businessCategoryId=?2 or iif.itemPackage.businessProfileCategory.businessCategory.businessCategoryId=?2)")
    List<ItemPackage> getItemPackagesBySearchItem(String txt, String category);

    @Query(value = "" +
            "select distinct i from ItemPackage i inner join i.itemPackageItemPackageFeatures iif inner join i.packageItem.packageItemItems pii " +
            "where i.available=true and i.name like ?1 or iif.itemPackageFeature.name like ?1 or pii.item.itemPackage.name like ?1")
    List<ItemPackage> getItemPackagesBySearchPackage(String txt);

    @Query(value = "" +
            "select distinct i from ItemPackage i inner join i.itemPackageItemPackageFeatures iif " +
            "where i.available=true and i.name like ?1 or iif.itemPackageFeature.name like ?1")
    List<ItemPackage> getItemPackagesBySearchItem(String txt);
}
