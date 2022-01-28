package lk.dbay.repository;

import lk.dbay.entity.CustomerItemFavourite;
import lk.dbay.entity.CustomerItemFavouritePK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerItemFavouriteR extends JpaRepository<CustomerItemFavourite, CustomerItemFavouritePK> {
}
