package lk.dbay.service;

import lk.dbay.dto.*;
import lk.dbay.entity.ItemOrder;
import lk.dbay.entity.OrderDetail;

import java.util.List;

public interface ItemOrderS {

    OrderDetailDTO addOrderDetail(OrderDetail orderDetail);

    ItemOrderDTO getInCompletedOrder(String customerId);

    OrderDetailDTO updateOrderDetail(OrderDetail orderDetail, String updateType);

    ItemOrderDTO placeOrder(ItemOrder itemOrder) throws Exception;

    List<ItemOrderDTO> getItemOrders(String businessProfileId, String businessCategoryId, String orderType);

    List<ItemOrderDTO> getPendingCustomerOrders(String customerId);
}
