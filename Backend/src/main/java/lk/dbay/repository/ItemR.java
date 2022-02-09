package lk.dbay.repository;

import lk.dbay.entity.BusinessProfileCategoryPK;
import lk.dbay.entity.item.Item;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ItemR extends JpaRepository<Item, String> {

    List<Item> getByItemCategory_ItemCategoryId(String categoryId);

    List<Item> getAllByItemPackage_BusinessProfileCategory_BusinessProfileCategoryId(BusinessProfileCategoryPK businessCategoryId);

    @Query(value = "from Item where itemPackage.businessProfileCategory.businessProfileCategoryId=?1 order by itemPackage.name")
    List<Item> getItemsOrdered(BusinessProfileCategoryPK businessProfileCategoryPK, Pageable pageable);

}
