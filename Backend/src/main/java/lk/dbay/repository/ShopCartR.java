package lk.dbay.repository;

import lk.dbay.entity.ShopCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopCartR extends JpaRepository<ShopCart, String> {
}
