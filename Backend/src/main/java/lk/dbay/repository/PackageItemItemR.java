package lk.dbay.repository;

import lk.dbay.entity.item.PackageItemItem;
import lk.dbay.entity.item.PackageItemItemPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PackageItemItemR extends JpaRepository<PackageItemItem, PackageItemItemPK> {
}
