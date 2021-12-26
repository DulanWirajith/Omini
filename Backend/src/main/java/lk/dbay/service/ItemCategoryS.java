package lk.dbay.service;

import lk.dbay.dto.ItemCategoryDTO;
import lk.dbay.dto.ItemPackageDTO;
import lk.dbay.entity.ItemCategory;
import lk.dbay.entity.ItemPackage;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ItemCategoryS {

    ItemCategoryDTO addCategory(ItemCategory itemCategory) throws Exception;

    ItemCategoryDTO updateCategory(ItemCategory itemCategory, String itemCategoryId) throws Exception;

    List<ItemCategoryDTO> getItemCategoriesOrdered(String businessProfileId, String businessCategoryId);

    ItemCategoryDTO getItemCategorySelected(String categoryId);
}
