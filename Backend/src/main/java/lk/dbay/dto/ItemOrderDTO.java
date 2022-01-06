package lk.dbay.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lk.dbay.entity.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
    private double discount;
    private int qty;
    private String status;
    private String contactNumber;
    private LocalDateTime receivedTime;
    private CustomerProfileDTO customerProfile;

    public ItemOrderDTO(ItemOrder itemOrder) {
        if (itemOrder != null) {
            this.orderId = itemOrder.getOrderId();
            this.orderDate = itemOrder.getOrderDate();
            this.amount = itemOrder.getAmount();
            this.discount = itemOrder.getDiscount();
            this.qty = itemOrder.getQty();
            this.status = itemOrder.getStatus();
            this.contactNumber = itemOrder.getContactNumber();
            this.receivedTime = itemOrder.getReceivedTime();
        }
    }
}
