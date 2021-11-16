package lk.dbay.repository;

import lk.dbay.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemR extends JpaRepository<Item,String> {
}
