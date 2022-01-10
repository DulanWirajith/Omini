package lk.dbay.repository;

import lk.dbay.entity.BusinessProfileCategoryPK;
import lk.dbay.entity.Item;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemR extends JpaRepository<Item, String> {

    List<Item> getAllByBusinessProfileCategory_BusinessProfileCategoryId(BusinessProfileCategoryPK businessCategoryId);

    @Query(value = "from Item where businessProfileCategory.businessProfileCategoryId=?1 order by name")
    List<Item> getItemsOrdered(BusinessProfileCategoryPK businessCategoryId, Pageable pageable);

    //    @Query(value = "from Item i inner join i.itemItemFeatures iif where (i.itemTitle like ?1 or iif.itemFeature.name like ?1) and (i.itemCategory.name like ?2 or iif.item.itemCategory.name like ?2)")
    @Query(value = "select distinct i from Item i inner join i.itemItemFeatures iif where (i.name like ?1 or iif.itemFeature.name like ?1) and (i.businessProfileCategory.businessCategory.businessCategoryId=?2 or iif.item.businessProfileCategory.businessCategory.businessCategoryId=?2)")
    List<Item> getItemsBySearch(String txt, String category);

    @Query(value = "select distinct i from Item i inner join i.itemItemFeatures iif where i.name like ?1 or iif.itemFeature.name like ?1")
    List<Item> getItemsBySearch(String txt);
}
