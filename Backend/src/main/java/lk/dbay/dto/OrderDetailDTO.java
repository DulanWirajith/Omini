package lk.dbay.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lk.dbay.entity.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Tolerate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDetailDTO extends DateTimeDTO {

    private String orderDetailId;
    private int quantity;
    private boolean makeToOrder;
    private double price;
    private double discountedPrice;
    private double discount;
    private String status;
    private boolean available;
    private String orderDetailType;
    private ItemOrderDTO itemOrder;
    private ItemPackageDTO itemPackage;
    private ItemDTO item;
    private BusinessProfileCategoryDTO businessProfileCategory;

    public OrderDetailDTO(OrderDetail orderDetail) {
        if (orderDetail != null) {
            this.orderDetailId = orderDetail.getOrderDetailId();
            this.quantity = orderDetail.getQuantity();
            this.price = orderDetail.getPrice();
            this.discount = orderDetail.getDiscount();
            this.orderDetailType = orderDetail.getOrderDetailType();
            this.status = orderDetail.getStatus();
            this.available = orderDetail.isAvailable();
            this.makeToOrder = orderDetail.isMakeToOrder();
        }
    }

    public void setItemOrder(OrderDetail orderDetail) {
        if (orderDetail.getItemOrder() != null) {
            this.itemOrder = new ItemOrderDTO(orderDetail.getItemOrder());
        }
    }

    @Tolerate
    public void setItemPackage(OrderDetail orderDetail) {
        if (orderDetail.getItemPackage() != null) {
            this.itemPackage = new ItemPackageDTO(orderDetail.getItemPackage());
        }
    }

    public void setBusinessProfileCategory(OrderDetail orderDetail) {
        if (orderDetail.getBusinessProfileCategory() != null) {
            this.businessProfileCategory = new BusinessProfileCategoryDTO(orderDetail.getBusinessProfileCategory().getBusinessProfile(), orderDetail.getBusinessProfileCategory().getBusinessCategory());
        }
    }

    //    @Tolerate
//    public void setItem(OrderDetail orderDetail) {
//        if (orderDetail.getItem() != null) {
//            this.item = new ItemDTO(orderDetail.getItem(), false);
//        }
//    }

    //    @ManyToOne
//    private ItemCategory itemCategory;
}
