package lk.dbay.repository;

import lk.dbay.entity.BusinessProfileCategory;
import lk.dbay.entity.BusinessProfileCategoryPK;
import lk.dbay.entity.ItemOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ItemOrderR extends JpaRepository<ItemOrder, String> {

    Optional<ItemOrder> getItemOrderByCustomerProfile_CustomerProIdAndStatus(String customerId, String status);

    List<ItemOrder> getItemOrdersByCustomerProfile_CustomerProId(String customerId);

//    @Query(value = "select distinct io from ItemOrder io inner join io.orderDetails ioo where (ioo.item.businessProfileCategory.businessProfileCategoryId=?1 or ioo.itemPackage.businessProfileCategory.businessProfileCategoryId=?1) and io.status=?2")
//    List<ItemOrder> getItemOrders(BusinessProfileCategoryPK businessProfileCategoryPK, String orderType);
//
//    @Query(value = "select distinct io from ItemOrder io inner join io.orderDetails ioo where io.status=?1")
//    List<ItemOrder> getItemOrders(String orderType);
}
