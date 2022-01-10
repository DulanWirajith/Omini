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
            itemOrderR.save(itemOrder);
        }
        orderDetail.setQuantity(orderDetail.getQuantity() + 1);
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
}
