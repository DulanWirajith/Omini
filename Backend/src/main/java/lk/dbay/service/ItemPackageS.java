package lk.dbay.service;

import lk.dbay.dto.ItemCategoryDTO;
import lk.dbay.dto.ItemPackageDTO;
import lk.dbay.entity.ItemCategory;
import lk.dbay.entity.ItemPackage;

public interface ItemPackageS {

    ItemPackageDTO addPackage(ItemPackage itemPackage);

}
