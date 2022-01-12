package lk.dbay.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lk.dbay.entity.*;
import lombok.*;
import lombok.experimental.Tolerate;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemOrderDTO extends DateTimeDTO {

    private String orderId;
    private LocalDateTime orderDate;
    private double amount;
//    private double discount;
    private int qty;
    private String status;
    private String contactNumber;
    private LocalDateTime receivedTime;
    private CustomerProfileDTO customerProfile;
    private List<OrderDetailDTO> orderDetails;

    public ItemOrderDTO(ItemOrder itemOrder) {
        if (itemOrder != null) {
            this.orderId = itemOrder.getOrderId();
            this.orderDate = itemOrder.getOrderDate();
//            this.amount = itemOrder.getAmount();
//            this.discount = itemOrder.getDiscount();
//            this.qty = itemOrder.getQty();
            this.status = itemOrder.getStatus();
            this.contactNumber = itemOrder.getContactNumber();
            this.receivedTime = itemOrder.getReceivedTime();
        }
    }

    public void setCustomerProfile(ItemOrder itemOrder) {
        if (itemOrder != null && itemOrder.getCustomerProfile() != null) {
            this.customerProfile = new CustomerProfileDTO(itemOrder.getCustomerProfile());
        }
    }

//    public void setOrderDetails(List<OrderDetailDTO> orderDetails) {
//        this.orderDetails = orderDetails;
//    }

    @Tolerate
    public void setOrderDetails(ItemOrder itemOrder) {
        if (itemOrder != null) {
            this.orderDetails = new ArrayList<>();
            for (OrderDetail orderDetail : itemOrder.getOrderDetails()) {
                this.orderDetails.add(new OrderDetailDTO(orderDetail));
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemOrderDTO that = (ItemOrderDTO) o;
        return Objects.equals(orderId, that.orderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId);
    }
}
