package lk.dbay.service;

import lk.dbay.dto.CountryDTO;
import lk.dbay.dto.RegionDTO;
import lk.dbay.dto.TownDTO;

import java.util.List;

public interface TownS {

    List<CountryDTO> getCountries();

    List<RegionDTO> getDistricts(String countryId);

    List<TownDTO> getTowns(String districtId);

    List<TownDTO> getTownsWIthDistrict();
}
