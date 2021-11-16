package lk.dbay.repository;

import lk.dbay.entity.BusinessProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessProfileR extends JpaRepository<BusinessProfile, String> {
}
