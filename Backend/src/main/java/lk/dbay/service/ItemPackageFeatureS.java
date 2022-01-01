package lk.dbay.service;

import lk.dbay.dto.ItemFeatureDTO;
import lk.dbay.dto.ItemPackageFeatureDTO;
import lk.dbay.entity.ItemPackageFeature;

import java.util.List;

public interface ItemPackageFeatureS {
    List<ItemPackageFeatureDTO> getItemPackageFeatures(String businessCategoryId);
}
