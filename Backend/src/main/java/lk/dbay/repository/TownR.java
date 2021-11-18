package lk.dbay.repository;

import lk.dbay.entity.Town;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TownR extends JpaRepository<Town, String> {

    List<Town> getAllByDistrict_DistrictId(String districtId);
}
