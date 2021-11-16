package lk.dbay.repository;

import lk.dbay.entity.Town;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TownR extends JpaRepository<Town, String> {
}
