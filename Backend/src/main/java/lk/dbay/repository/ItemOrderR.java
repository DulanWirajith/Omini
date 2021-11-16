package lk.dbay.repository;

import lk.dbay.entity.ItemOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemOrderR extends JpaRepository<ItemOrder, String> {
}
