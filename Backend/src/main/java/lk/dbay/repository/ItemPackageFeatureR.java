package lk.dbay.repository;

import lk.dbay.entity.item.ItemPackageFeature;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemPackageFeatureR extends JpaRepository<ItemPackageFeature, String> {

//    List<ItemPackageFeature> getAllByBusinessCategory_BusinessCategoryIdAndConfirmed(String businessCategoryId, boolean confirmed);

    List<ItemPackageFeature> getAllByBusinessCategory_BusinessCategoryId(String businessCategoryId);

}
