package lk.dbay.repository;

import lk.dbay.entity.BusinessProfileCategoryPK;
import lk.dbay.entity.Item;
import lk.dbay.entity.ItemCategory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemCategoryR extends JpaRepository<ItemCategory,String> {

    @Query(value = "from ItemCategory where businessProfileCategory.businessProfileCategoryId=?1 order by name")
    List<ItemCategory> getCategoriesOrdered(BusinessProfileCategoryPK businessCategoryId, Pageable pageable);
}
