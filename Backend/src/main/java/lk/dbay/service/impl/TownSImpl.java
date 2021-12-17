package lk.dbay.service.impl;

import lk.dbay.dto.CountryDTO;
import lk.dbay.dto.DistrictDTO;
import lk.dbay.dto.TownDTO;
import lk.dbay.entity.Country;
import lk.dbay.entity.District;
import lk.dbay.entity.Town;
import lk.dbay.repository.CountryR;
import lk.dbay.repository.DistrictR;
import lk.dbay.repository.TownR;
import lk.dbay.service.TownS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TownSImpl implements TownS {

    @Autowired
    private CountryR countryR;
    @Autowired
    private DistrictR districtR;
    @Autowired
    private TownR townR;

    @Override
    public List<CountryDTO> getCountries() {
        List<Country> countryRAll = countryR.findAll();
        List<CountryDTO> countryDTOS = new ArrayList<>();
        for (Country country : countryRAll) {
            countryDTOS.add(new CountryDTO(country));
        }
        return countryDTOS;
    }

    @Override
    public List<DistrictDTO> getDistricts(String countryId) {
        List<District> districtRAll = districtR.getAllByCountry_CountryId(countryId);
        List<DistrictDTO> districtDTOS = new ArrayList<>();
        for (District district : districtRAll) {
            districtDTOS.add(new DistrictDTO(district));
        }
        return districtDTOS;
    }

    @Override
    public List<TownDTO> getTowns(String districtId) {
        List<Town> townRAll = townR.getAllByDistrict_DistrictId(districtId);
        List<TownDTO> townDTOS = new ArrayList<>();
        for (Town town : townRAll) {
            townDTOS.add(new TownDTO(town));
        }
        return townDTOS;
    }

    @Override
    public List<TownDTO> getTownsWIthDistrict() {
        List<Town> townRAll = townR.findAll();
        List<TownDTO> townDTOS = new ArrayList<>();
        for (Town town : townRAll) {
//            TownDTO townDTO = new TownDTO();
//            townDTO.setTownId(town.getTownId());
//            townDTO.setName(town.getDistrict().getName() + "-" + town.getName());
            townDTOS.add(new TownDTO(town));
        }
        return townDTOS;
    }
}
