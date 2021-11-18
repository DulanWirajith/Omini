package lk.dbay.repository;

import lk.dbay.entity.District;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DistrictR extends JpaRepository<District, String> {

    List<District> getAllByCountry_CountryId(String countryId);
}
