package lk.dbay.repository;

import lk.dbay.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserR extends JpaRepository<User, String> {
}
