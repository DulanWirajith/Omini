package lk.dbay.repository;

import lk.dbay.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationR extends JpaRepository<Notification,String> {
}
