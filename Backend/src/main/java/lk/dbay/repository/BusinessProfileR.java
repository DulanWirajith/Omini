package lk.dbay.repository;

import lk.dbay.entity.BusinessProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BusinessProfileR extends JpaRepository<BusinessProfile, String> {

    Optional<BusinessProfile> getByDbayUser_UserId(String userId);

}
