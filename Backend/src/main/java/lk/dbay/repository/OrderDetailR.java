package lk.dbay.repository;

import lk.dbay.entity.BusinessProfileCategoryPK;
import lk.dbay.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

//    @Query(value = "from OrderDetail where item.businessProfileCategory.businessProfileCategoryId=?1 and itemOrder.status=?2")
//    List<OrderDetail> getItemOrderDetailsItems(BusinessProfileCategoryPK businessProfileCategoryPK, String orderType);
//
//    @Query(value = "from OrderDetail where itemPackage.businessProfileCategory.businessProfileCategoryId=?1 and itemOrder.status=?2")
//    List<OrderDetail> getItemOrderDetailsItemPackages(BusinessProfileCategoryPK businessProfileCategoryPK, String orderType);

    @Query(value = "from OrderDetail where businessProfileCategory.businessProfileCategoryId=?1 and (status=?2 or status=?3) and itemOrder.status <> 'NotFinalized'")
    List<OrderDetail> getItemOrderDetailsByOrderType(BusinessProfileCategoryPK businessProfileCategoryPK, String orderType1, String orderType2);

    @Query(value = "from OrderDetail where businessProfileCategory.businessProfileCategoryId=?1 and status=?2 and (itemOrder.orderDate>=?3 and itemOrder.orderDate<=?4) and itemOrder.status <> 'NotFinalized'")
    List<OrderDetail> getItemOrderDetailsByDate(BusinessProfileCategoryPK businessProfileCategoryPK, String status, LocalDateTime from, LocalDateTime to);

//    @Query(value = "from OrderDetail where businessProfileCategory.businessProfileCategoryId=?1 and status=?2 and itemOrder.status <> 'NotFinalized'")
//    List<OrderDetail> getItemOrderDetails(BusinessProfileCategoryPK businessProfileCategoryPK, String status);

    @Query(value = "from OrderDetail where itemOrder.orderId=?1 and businessProfileCategory.businessProfileCategoryId=?2 and itemOrder.status <> 'NotFinalized'")
    List<OrderDetail> changeOrderStatus(String orderId, BusinessProfileCategoryPK businessProfileCategoryPK);

    @Query(value = "from OrderDetail where itemOrder.customerProfile.customerProId=?1 and (status=?2 or status=?3) and (itemOrder.orderDate>=?4 and itemOrder.orderDate<=?5) and itemOrder.status <> 'NotFinalized'")
    List<OrderDetail> getCustomerOrderDetails(String customerId, String status1, String status2, LocalDateTime from, LocalDateTime to);

//    @Query(value = "select count(orderDetailId) from OrderDetail where businessProfileCategory.businessProfileCategoryId=?1 and itemOrder.status=?2")
//    int getPendingOrdersCount(BusinessProfileCategoryPK businessProfileCategoryPK, String orderType);

//    @Query(value = "from OrderDetail where itemPackage.itemOrder.customerProfile.customerProId=?1 and itemOrder.status=?2")
//    List<OrderDetail> getItemOrderDetailsItemPackages(String customerId);
}
