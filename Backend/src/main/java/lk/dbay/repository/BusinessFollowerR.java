package lk.dbay.repository;

import lk.dbay.entity.BusinessFollower;
import lk.dbay.entity.BusinessFollowerPK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BusinessFollowerR extends JpaRepository<BusinessFollower, BusinessFollowerPK> {

    Optional<BusinessFollower> getByCustomerProfile_CustomerProIdAndBusinessProfile_BusinessProId(String customerId, String businessProId);

    List<BusinessFollower> getAllByCustomerProfile_CustomerProId(String customerId);
}
