package lk.dbay.repository;

import lk.dbay.entity.BusinessProfileCategoryPK;
import lk.dbay.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderDetailR extends JpaRepository<OrderDetail, String> {

    //    @Query(value = "from OrderDetail where (item.businessProfileCategory.businessProfileCategoryId=?1 or itemPackage.businessProfileCategory.businessProfileCategoryId=?1) and itemOrder.status=?2")
//    @Query(value = "" +
//            "from OrderDetail od " +
//            "inner join Item it on (od.item.itemId=it.itemId or od.item.itemId is null) " +
//            "inner join ItemPackage ip on (od.itemPackage.itemPackageId=ip.itemPackageId or od.itemPackage.itemPackageId is null) " +
//            "inner join ItemOrder io on od.itemOrder.orderId=io.orderId " +
//            "where (od.item.businessProfileCategory.businessProfileCategoryId=?1 or od.itemPackage.businessProfileCategory.businessProfileCategoryId=?1) and od.itemOrder.status=?2")
//    List<OrderDetail> getItemOrderDetails(BusinessProfileCategoryPK businessProfileCategoryPK, String orderType);

    @Query(value = "from OrderDetail where item.businessProfileCategory.businessProfileCategoryId=?1 and itemOrder.status=?2")
    List<OrderDetail> getItemOrderDetailsItems(BusinessProfileCategoryPK businessProfileCategoryPK, String orderType);

    @Query(value = "from OrderDetail where itemPackage.businessProfileCategory.businessProfileCategoryId=?1 and itemOrder.status=?2")
    List<OrderDetail> getItemOrderDetailsItemPackages(BusinessProfileCategoryPK businessProfileCategoryPK, String orderType);
}
