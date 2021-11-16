package lk.dbay.repository;

import lk.dbay.entity.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComplaintR extends JpaRepository<Complaint, String> {
}
