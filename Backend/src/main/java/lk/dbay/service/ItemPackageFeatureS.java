package lk.dbay.service;

import lk.dbay.dto.ItemPackageFeatureDTO;

import java.util.List;

public interface ItemPackageFeatureS {
    List<ItemPackageFeatureDTO> getItemPackageFeatures(String businessCategoryId);
}