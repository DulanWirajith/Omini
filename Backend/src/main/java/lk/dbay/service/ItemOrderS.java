package lk.dbay.service;

import lk.dbay.dto.*;
import lk.dbay.entity.ItemOrder;
import lk.dbay.entity.OrderDetail;

public interface ItemOrderS {

    OrderDetailDTO addOrderDetail(OrderDetail orderDetail);

    ItemOrderDTO getInCompletedOrder(String customerId);

    OrderDetailDTO updateOrderDetail(OrderDetail orderDetail, String updateType);

    ItemOrderDTO placeOrder(ItemOrder itemOrder) throws Exception;
}
