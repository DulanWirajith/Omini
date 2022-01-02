package lk.dbay.repository;

import lk.dbay.entity.ItemFeature;
import lk.dbay.entity.ItemPackageFeature;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemPackageFeatureR extends JpaRepository<ItemPackageFeature, String> {

    List<ItemPackageFeature> getAllByBusinessCategory_BusinessCategoryIdAndConfirmed(String businessCategoryId, boolean confirmed);

}
