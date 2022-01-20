package lk.dbay.service.impl;

import lk.dbay.dto.*;
import lk.dbay.entity.*;
import lk.dbay.repository.*;
import lk.dbay.service.ItemOrderS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class ItemOrderSImpl implements ItemOrderS {

    @Autowired
    private ItemOrderR itemOrderR;
    @Autowired
    private OrderDetailR orderDetailR;
    @Autowired
    private ItemR itemR;

    @Override
    public OrderDetailDTO addOrderDetail(OrderDetail orderDetail) {
        if (orderDetail.getItemOrder().getOrderId().equals("")) {
            LocalDateTime localDateTime = LocalDateTime.now();
            String format = localDateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            ItemOrder itemOrder = orderDetail.getItemOrder();
            itemOrder.setOrderId("OD" + format);
            itemOrder.setStatus("NotFinalized");
//            itemOrder.setQty(1);
//            itemOrder.setAmount(orderDetail.getDiscountedPrice());
//            itemOrder.getBusinessProfileCategory().setBusinessProfileCategoryId(new BusinessProfileCategoryPK(itemOrder.getBusinessProfileCategory().getBusinessProfile().getBusinessProId(), itemOrder.getBusinessProfileCategory().getBusinessCategory().getBusinessCategoryId()));
            itemOrderR.save(itemOrder);
        }
        orderDetail.getBusinessProfileCategory().setBusinessProfileCategoryId(new BusinessProfileCategoryPK(
                orderDetail.getBusinessProfileCategory().getBusinessProfile().getBusinessProId(),
                orderDetail.getBusinessProfileCategory().getBusinessCategory().getBusinessCategoryId()));
        orderDetail.setQuantity(orderDetail.getQuantity() + 1);
        orderDetail.setStatus("Pending");
        if (orderDetail.getItem() != null) {
            orderDetail.setOrderDetailId("ODD" + orderDetail.getItemOrder().getOrderId() + orderDetail.getItem().getItemId());
            orderDetail.setOrderDetailType("Item");
        } else if (orderDetail.getItemPackage() != null) {
            orderDetail.setOrderDetailId("ODD" + orderDetail.getItemOrder().getOrderId() + orderDetail.getItemPackage().getItemPackageId());
            orderDetail.setOrderDetailType("ItemPackage");
        }
//        orderDetail.getItemOrder().getOrderDetails().add(orderDetail);
        orderDetailR.save(orderDetail);
        OrderDetailDTO orderDetailDTO = new OrderDetailDTO(orderDetail);
        orderDetailDTO.setItemOrder(orderDetail);
        orderDetailDTO.setItemPackage(orderDetail);
        return orderDetailDTO;
//        if (itemOrder.getOrderId().equals("")) {
//            itemOrder.setOrderId("OD" + format);
//            itemOrder.setStatus("NotFinalized");
//            for (OrderDetail orderDetail : itemOrder.getOrderDetails()) {
//                orderDetail.setOrderDetailId("ODD" + format);
//                orderDetail.setQuantity(orderDetail.getQuantity() + 1);
//                if (orderDetail.getItem() != null) {
//                    orderDetail.setOrderDetailType("Item");
//                } else if (orderDetail.getItemPackage() != null) {
//                    orderDetail.setOrderDetailType("ItemPackage");
//                }
//                orderDetail.setItemOrder(itemOrder);
//            }
//            itemOrderR.save(itemOrder);
//        } else {
//            for (OrderDetail orderDetail : itemOrder.getOrderDetails()) {
//                if (!orderDetail.getOrderDetailId().equals("")) {
//                    Optional<OrderDetail> orderDetailOptional = orderDetailR.findById(orderDetail.getOrderDetailId());
//                    if (orderDetailOptional.isPresent()) {
//                        OrderDetail orderDetailObj = orderDetailOptional.get();
//                        orderDetailObj.setQuantity(orderDetailObj.getQuantity() + 1);
//                        orderDetailR.save(orderDetailObj);
//                    }
//                } else {
//                    orderDetail.setOrderDetailId("ODD" + format);
//                    orderDetail.setQuantity(orderDetail.getQuantity() + 1);
//                    if (orderDetail.getItem() != null) {
//                        orderDetail.setOrderDetailType("Item");
//                    } else if (orderDetail.getItemPackage() != null) {
//                        orderDetail.setOrderDetailType("ItemPackage");
//                    }
//                    orderDetail.setItemOrder(itemOrder);
//                    itemOrderR.save(itemOrder);
//                }
//            }
//        }

//        ItemOrderDTO itemOrderDTO = new ItemOrderDTO(itemOrder);
//        itemOrderDTO.setOrderDetails(itemOrder);
//        return itemOrderDTO;
    }

    @Override
    public ItemOrderDTO getInCompletedOrder(String customerId) {
        Optional<ItemOrder> itemOrderOptional = itemOrderR.getItemOrderByCustomerProfile_CustomerProIdAndStatus(customerId, "NotFinalized");
        if (itemOrderOptional.isPresent()) {
            ItemOrder itemOrder = itemOrderOptional.get();
            ItemOrderDTO itemOrderDTO = new ItemOrderDTO(itemOrder);
            List<OrderDetailDTO> orderDetailDTOS = new ArrayList<>();
            for (OrderDetail orderDetail : itemOrder.getOrderDetails()) {
                OrderDetailDTO orderDetailDTO = new OrderDetailDTO(orderDetail);
                if (orderDetail.getOrderDetailType().equals("Item")) {
                    ItemDTO itemDTO = new ItemDTO(orderDetail.getItem(), false);
                    BusinessProfileCategoryDTO businessProfileCategoryDTO = new BusinessProfileCategoryDTO(orderDetail.getItem().getBusinessProfileCategory().getBusinessProfile(), null);
                    itemDTO.setBusinessProfileCategory(businessProfileCategoryDTO);
                    orderDetailDTO.setItem(itemDTO);
                } else if (orderDetail.getOrderDetailType().equals("ItemPackage")) {
                    ItemPackageDTO itemPackageDTO = new ItemPackageDTO(orderDetail.getItemPackage());
                    BusinessProfileCategoryDTO businessProfileCategoryDTO = new BusinessProfileCategoryDTO(orderDetail.getItemPackage().getBusinessProfileCategory().getBusinessProfile(), null);
                    itemPackageDTO.setBusinessProfileCategory(businessProfileCategoryDTO);
                    orderDetailDTO.setItemPackage(itemPackageDTO);
                }
                orderDetailDTOS.add(orderDetailDTO);
            }

            itemOrderDTO.setOrderDetails(orderDetailDTOS);
            return itemOrderDTO;
        }
        return null;
    }

    @Override
    public OrderDetailDTO updateOrderDetail(OrderDetail orderDetail, String updateType) {
        String itemId = "";
        if (orderDetail.getOrderDetailType().equals("Item")) {
            itemId = orderDetail.getItem().getItemId();
        } else if (orderDetail.getOrderDetailType().equals("ItemPackage")) {
            itemId = orderDetail.getItemPackage().getItemPackageId();
        }
        Optional<OrderDetail> orderDetailOptional = orderDetailR.findById("ODD" + orderDetail.getItemOrder().getOrderId() + itemId);
        if (orderDetailOptional.isPresent()) {
            OrderDetail orderDetailObj = orderDetailOptional.get();
            if (updateType.equals("inc")) {
                orderDetailObj.setQuantity(orderDetail.getQuantity() + 1);
            } else if (updateType.equals("dec")) {
                orderDetailObj.setQuantity(orderDetail.getQuantity() - 1);
            }
            orderDetail.getBusinessProfileCategory().setBusinessProfileCategoryId(new BusinessProfileCategoryPK(
                    orderDetail.getBusinessProfileCategory().getBusinessProfile().getBusinessProId(),
                    orderDetail.getBusinessProfileCategory().getBusinessCategory().getBusinessCategoryId()));
//            orderDetail.getItemOrder().setQty(orderDetail.getItemOrder().getQty() + 1);
//            orderDetail.getItemOrder().setAmount(orderDetail.getItemOrder().getAmount() + orderDetail.getDiscountedPrice());
//            itemOrderR.save(orderDetail.getItemOrder());
            orderDetailR.save(orderDetailObj);
            OrderDetailDTO orderDetailDTO = new OrderDetailDTO(orderDetailObj);
            orderDetailDTO.setItemOrder(orderDetailObj);
            orderDetailDTO.setItemPackage(orderDetailObj);
            return orderDetailDTO;
        }
        return null;
    }

    @Override
    @Transactional
    public ItemOrderDTO placeOrder(ItemOrder itemOrder) throws Exception {
        Optional<ItemOrder> itemOrderOptional = itemOrderR.findById(itemOrder.getOrderId());
        if (itemOrderOptional.isPresent()) {
            ItemOrder itemOrderObj = itemOrderOptional.get();
            itemOrderObj.setStatus("Pending");
            itemOrderObj.setOrderDate(LocalDateTime.now());
            itemOrderR.save(itemOrderObj);
            List<Item> items = new ArrayList<>();
            String itemsNotAvailable = "";
            for (OrderDetail orderDetail : itemOrder.getOrderDetails()) {
                if (orderDetail.getOrderDetailType().equals("Item")) {
                    Optional<Item> itemOptional = itemR.findById(orderDetail.getItem().getItemId());
                    if (itemOptional.isPresent()) {
                        Item item = itemOptional.get();
                        int itemQty = item.getQuantity() - orderDetail.getQuantity();
                        if (itemQty > 0) {
                            item.setQuantity(itemQty);
                            items.add(item);
                        } else {
                            itemsNotAvailable += item.getName() + ", ";
                        }
                    }
                }
            }
            if (!itemsNotAvailable.equals("")) {
                throw new Exception(itemsNotAvailable + " not available in stock now.");
            }
            if (items.size() > 0) {
                itemR.saveAll(items);
            }
            itemR.saveAll(items);
            return new ItemOrderDTO(itemOrderObj);
        }
        return null;
    }

    @Override
    public List<ItemOrderDTO> getItemOrders(String businessProfileId, String businessCategoryId) {
        List<OrderDetail> itemOrderDetailsItems = orderDetailR.getItemOrderDetails(new BusinessProfileCategoryPK(businessProfileId, businessCategoryId));
        return setItemOrders(itemOrderDetailsItems, false);
    }

    @Override
    public List<ItemOrderDTO> getCustomerOrders(String customerId) {
        List<OrderDetail> itemOrderDetailsItems = orderDetailR.getCustomerOrderDetails(customerId, "Pending", "In Progress");
        return setItemOrders(itemOrderDetailsItems, true);
    }

    @Override
    public ItemOrderDTO changeOrderStatus(String orderId, String businessProfileId, String businessCategoryId, String status) {
        List<OrderDetail> orderDetails = orderDetailR.changeOrderStatus(orderId, new BusinessProfileCategoryPK(businessProfileId, businessCategoryId));
        for (OrderDetail orderDetail : orderDetails) {
            orderDetail.setStatus(status);
//            orderDetailR.save(orderDetail);
        }
        orderDetailR.saveAll(orderDetails);
        ItemOrderDTO itemOrderDTO = new ItemOrderDTO();
        itemOrderDTO.setOrderId(orderId);
        itemOrderDTO.setStatus(status);
        return itemOrderDTO;
//        Optional<ItemOrder> itemOrderOptional = itemOrderR.findById(orderId);
//        if (itemOrderOptional.isPresent()) {
//            ItemOrder itemOrder = itemOrderOptional.get();
//            itemOrder.setStatus(status);
//            return new ItemOrderDTO(itemOrderR.save(itemOrder));
//        }
//        return null;
    }

    @Override
    public boolean acceptItem(String orderDetailId) {
        Optional<OrderDetail> orderDetailOptional = orderDetailR.findById(orderDetailId);
        if (orderDetailOptional.isPresent()) {
            OrderDetail orderDetail = orderDetailOptional.get();
            orderDetail.setAvailable(!orderDetail.isAvailable());
            orderDetailR.save(orderDetail);
            return orderDetail.isAvailable();
        }
        return false;
    }

    private List<ItemOrderDTO> setItemOrders(List<OrderDetail> itemOrderDetailsItems, boolean needBusinessProfile) {
        Set<ItemOrderDTO> itemOrderDTOS = new HashSet<>();
        for (OrderDetail itemOrderDetail : itemOrderDetailsItems) {
            itemOrderDTOS.add(new ItemOrderDTO(itemOrderDetail.getItemOrder()));
        }
//        if (itemOrderDetailsItemPackages != null) {
//            for (OrderDetail itemOrderDetail : itemOrderDetailsItemPackages) {
//                itemOrderDTOS.add(new ItemOrderDTO(itemOrderDetail.getItemOrder()));
//            }
//        }
        for (ItemOrderDTO itemOrderDTO : itemOrderDTOS) {
            itemOrderDTO.setOrderDetails(new ArrayList<>());
            itemOrderDTO.setAmount(0);
            itemOrderDTO.setQty(0);
            for (OrderDetail itemOrderDetail : itemOrderDetailsItems) {
//                if (itemOrderDetail.getStatus().equals("Pending")) {

//                }
                if (itemOrderDTO.getOrderId().equals(itemOrderDetail.getItemOrder().getOrderId())) {
                    itemOrderDTO.setStatus(itemOrderDetail.getStatus());
                    OrderDetailDTO orderDetailDTO = new OrderDetailDTO(itemOrderDetail);
                    if (itemOrderDetail.getItem() != null) {
                        orderDetailDTO.setOrderDetailType("Item");
                        orderDetailDTO.setItem(new ItemDTO(itemOrderDetail.getItem(), false));
                        if (needBusinessProfile) {
                            orderDetailDTO.setBusinessProfileCategory(itemOrderDetail);
                        }

                    } else if (itemOrderDetail.getItemPackage() != null) {
                        orderDetailDTO.setOrderDetailType("ItemPackage");
                        orderDetailDTO.setItemPackage(new ItemPackageDTO(itemOrderDetail.getItemPackage()));
                        if (needBusinessProfile) {
                            orderDetailDTO.setBusinessProfileCategory(itemOrderDetail);
                        }
                    }
                    itemOrderDTO.getOrderDetails().add(orderDetailDTO);
                    itemOrderDTO.setAmount(itemOrderDTO.getAmount() + (itemOrderDetail.getPrice() * itemOrderDetail.getQuantity()));
                    itemOrderDTO.setQty(itemOrderDTO.getQty() + itemOrderDetail.getQuantity());
                    itemOrderDTO.setCustomerProfile(itemOrderDetail.getItemOrder());
                }
            }
//            if (itemOrderDetailsItemPackages != null) {
//                for (OrderDetail itemOrderDetail : itemOrderDetailsItemPackages) {
//                    if (itemOrderDTO.getOrderId().equals(itemOrderDetail.getItemOrder().getOrderId())) {
//                        OrderDetailDTO orderDetailDTO = new OrderDetailDTO(itemOrderDetail);
//                        orderDetailDTO.setItemPackage(new ItemPackageDTO(itemOrderDetail.getItemPackage()));
//                        orderDetailDTO.setOrderDetailType("ItemPackage");
//                        itemOrderDTO.getOrderDetails().add(orderDetailDTO);
//                        itemOrderDTO.setAmount(itemOrderDTO.getAmount() + (itemOrderDetail.getPrice() * itemOrderDetail.getQuantity()));
//                        itemOrderDTO.setQty(itemOrderDTO.getQty() + itemOrderDetail.getQuantity());
//                        itemOrderDTO.setCustomerProfile(itemOrderDetail.getItemOrder());
//                    }
//                }
//            }
        }
        return new ArrayList<>(itemOrderDTOS);
    }
}
