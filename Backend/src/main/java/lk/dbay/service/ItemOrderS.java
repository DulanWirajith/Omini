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

    List<ItemOrderDTO> getItemOrders(String businessProfileId, String businessCategoryId, String status, String from, String to);

    List<ItemOrderDTO> getCustomerOrders(String customerId, String status, String from, String to);

    ItemOrderDTO changeOrderStatus(String orderId, String businessProfileId, String businessCategoryId, String status);

    boolean acceptItem(String orderDetailId);

    boolean removeCartItem(String orderDetailId) throws Exception;

    boolean removeCart(String orderId);

    boolean removeCartShop(String orderId, String businessProfileId, String businessCategoryId);
}