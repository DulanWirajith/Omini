package lk.dbay.repository;

import lk.dbay.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegionR extends JpaRepository<Region, String> {

    List<Region> getAllByCountry_CountryId(String countryId);
}
