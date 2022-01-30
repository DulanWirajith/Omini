package lk.dbay.repository;

import lk.dbay.entity.item.ItemPackageFavourite;
import lk.dbay.entity.item.ItemPackageFavouritePK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerItemFavouriteR extends JpaRepository<ItemPackageFavourite, ItemPackageFavouritePK> {
}
