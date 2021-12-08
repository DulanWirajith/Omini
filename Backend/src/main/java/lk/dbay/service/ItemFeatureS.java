package lk.dbay.service;

import lk.dbay.dto.ItemFeatureDTO;

import java.util.List;

public interface ItemFeatureS {
    List<ItemFeatureDTO> getItemFeatures(String businessCategoryId);
}
