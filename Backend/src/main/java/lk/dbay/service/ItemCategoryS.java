package lk.dbay.service;

import lk.dbay.dto.ItemCategoryDTO;
import lk.dbay.dto.ItemPackageDTO;
import lk.dbay.entity.ItemCategory;
import lk.dbay.entity.ItemPackage;

import java.util.List;

public interface ItemCategoryS {

    ItemCategoryDTO addCategory(ItemCategory itemCategory) throws Exception;

    List<ItemCategoryDTO> getCategoriesOrdered(String businessProfileId, String businessCategoryId, int start, int limit);

    ItemCategoryDTO getCategorySelected(String categoryId);
}
