package lk.dbay.repository;

import lk.dbay.entity.ItemCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemCategoryR extends JpaRepository<ItemCategory,String> {
}
