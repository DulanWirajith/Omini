package lk.dbay.repository;

import lk.dbay.entity.item.ItemPackageReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemPackageReviewR extends JpaRepository<ItemPackageReview, String> {

//    List<ItemPackageReview> getAllByItemPackage_ItemPackageIdAndReviewType(String itemId, String reviewType);

    List<ItemPackageReview> getAllByItemPackage_ItemPackageId(String itemPackageId);
}
