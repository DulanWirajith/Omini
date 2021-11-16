package lk.dbay.repository;

import lk.dbay.entity.ItemItemCategory;
import lk.dbay.entity.ItemItemCategoryPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemItemCategoryR extends JpaRepository<ItemItemCategory, ItemItemCategoryPK> {
}
