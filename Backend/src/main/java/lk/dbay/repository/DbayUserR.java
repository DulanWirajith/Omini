package lk.dbay.repository;

import lk.dbay.entity.DbayUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DbayUserR extends JpaRepository<DbayUser, String> {
}
