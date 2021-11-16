package lk.dbay.repository;

import lk.dbay.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailR extends JpaRepository<OrderDetail,String> {
}
