package lk.dbay.repository;

import lk.dbay.entity.CustomerProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerProfileR extends JpaRepository<CustomerProfile, String> {
}
