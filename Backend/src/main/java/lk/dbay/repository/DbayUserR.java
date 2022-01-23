package lk.dbay.repository;

import lk.dbay.entity.DbayUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DbayUserR extends JpaRepository<DbayUser, String> {

    @Query(value = "from DbayUser where (email=?1 or username=?1)")
    DbayUser getUserByUsernameOrEmailAndVerificationCode(String username);

    DbayUser findByEmail(String email);
}
