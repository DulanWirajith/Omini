package lk.dbay.service.impl;

import lk.dbay.dto.ItemFeatureDTO;
import lk.dbay.entity.ItemFeature;
import lk.dbay.repository.ItemFeatureR;
import lk.dbay.service.ItemFeatureS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemFeatureSImpl implements ItemFeatureS {

    @Autowired
    private ItemFeatureR itemFeatureR;

    @Override
    public List<ItemFeatureDTO> getItemFeatures(String businessCategoryId) {
        List<ItemFeature> itemFeatureRAll = itemFeatureR.getAllByBusinessCategory_BusinessCategoryId(businessCategoryId);
        List<ItemFeatureDTO> itemFeatureDTOS = new ArrayList<>();
        for (ItemFeature itemFeature : itemFeatureRAll) {
            itemFeatureDTOS.add(new ItemFeatureDTO(itemFeature));
        }
        return itemFeatureDTOS;
    }
}
