package lk.dbay.repository;

import lk.dbay.entity.ItemFeature;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemFeatureR extends JpaRepository<ItemFeature, String> {

    List<ItemFeature> getAllByBusinessCategory_BusinessCategoryId(String businessCategoryId);

}
