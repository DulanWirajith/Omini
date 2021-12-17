package lk.dbay.repository;

import lk.dbay.entity.BusinessProfileCategoryPK;
import lk.dbay.entity.Item;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemR extends JpaRepository<Item, String> {

    List<Item> getAllByBusinessProfileCategory_BusinessProfileCategoryId(BusinessProfileCategoryPK businessCategoryId);

    @Query(value = "from Item where businessProfileCategory.businessProfileCategoryId=?1 order by itemTitle")
    List<Item> getItemsOrdered(BusinessProfileCategoryPK businessCategoryId, Pageable pageable);

}
