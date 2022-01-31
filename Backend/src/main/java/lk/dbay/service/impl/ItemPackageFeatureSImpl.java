package lk.dbay.service.impl;

import lk.dbay.dto.ItemPackageFeatureDTO;
import lk.dbay.entity.item.ItemPackageFeature;
import lk.dbay.repository.ItemPackageFeatureR;
import lk.dbay.service.ItemPackageFeatureS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemPackageFeatureSImpl implements ItemPackageFeatureS {

    @Autowired
    private ItemPackageFeatureR itemPackageFeatureR;

    @Override
    public List<ItemPackageFeatureDTO> getItemPackageFeatures(String businessCategoryId) {
        List<ItemPackageFeature> itemFeatureRAll = itemPackageFeatureR.getAllByBusinessCategory_BusinessCategoryIdAndConfirmed(businessCategoryId, true);
        List<ItemPackageFeatureDTO> itemPackageFeatureDTOS = new ArrayList<>();
        for (ItemPackageFeature itemPackageFeature : itemFeatureRAll) {
            itemPackageFeatureDTOS.add(new ItemPackageFeatureDTO(itemPackageFeature));
        }
        return itemPackageFeatureDTOS;
    }
}