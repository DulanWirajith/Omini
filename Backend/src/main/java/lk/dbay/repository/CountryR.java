package lk.dbay.repository;

import lk.dbay.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryR extends JpaRepository<Country, String> {
}
