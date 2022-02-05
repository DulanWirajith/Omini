package lk.dbay.repository;

import lk.dbay.entity.item.ItemPackageFavourite;
import lk.dbay.entity.item.ItemPackageFavouritePK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemPackageFavouriteR extends JpaRepository<ItemPackageFavourite, ItemPackageFavouritePK> {

    Optional<ItemPackageFavourite> getByCustomerProfile_CustomerProIdAndItemPackage_ItemPackageId(String customerId, String itemPackageId);

    List<ItemPackageFavourite> getAllByCustomerProfile_CustomerProId(String customerId);
}
