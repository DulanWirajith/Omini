package lk.dbay.repository;

import lk.dbay.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionR extends JpaRepository<Subscription,String> {
}
