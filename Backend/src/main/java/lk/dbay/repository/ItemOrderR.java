package lk.dbay.repository;

import lk.dbay.entity.ItemOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemOrderR extends JpaRepository<ItemOrder, String> {

    Optional<ItemOrder> getItemOrderByCustomerProfile_CustomerProIdAndStatus(String customerId, String status);

}
