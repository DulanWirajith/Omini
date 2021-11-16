package lk.dbay.repository;

import lk.dbay.entity.BusinessSubscription;
import lk.dbay.entity.BusinessSubscriptionPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessSubscriptionR extends JpaRepository<BusinessSubscription, BusinessSubscriptionPK> {
}
