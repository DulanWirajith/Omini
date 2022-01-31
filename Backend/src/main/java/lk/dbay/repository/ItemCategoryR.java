package lk.dbay.repository;

import lk.dbay.entity.BusinessProfileCategoryPK;
import lk.dbay.entity.item.ItemCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemCategoryR extends JpaRepository<ItemCategory,String> {

    @Query(value = "from ItemCategory where businessProfileCategory.businessProfileCategoryId=?1 order by name")
    List<ItemCategory> getItemCategoriesOrdered(BusinessProfileCategoryPK businessCategoryId);
}