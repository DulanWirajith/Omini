package lk.dbay.repository;

import lk.dbay.entity.BusinessProfileCategoryPK;
import lk.dbay.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemR extends JpaRepository<Item, String> {

    List<Item> getAllByBusinessProfileCategory_BusinessProfileCategoryId(BusinessProfileCategoryPK businessCategoryId);

}
