package lk.dbay.service;

import lk.dbay.dto.CountryDTO;
import lk.dbay.dto.DistrictDTO;
import lk.dbay.dto.TownDTO;

import java.util.List;

public interface TownS {

    List<CountryDTO> getCountries();

    List<DistrictDTO> getDistricts(String countryId);

    List<TownDTO> getTowns(String districtId);

    List<TownDTO> getTownsWIthDistrict();
}
