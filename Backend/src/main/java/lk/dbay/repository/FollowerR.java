package lk.dbay.repository;

import lk.dbay.entity.Follower;
import lk.dbay.entity.FollowerPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowerR extends JpaRepository<Follower, FollowerPK> {
}
