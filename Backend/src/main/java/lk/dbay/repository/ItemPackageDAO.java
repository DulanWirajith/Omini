package lk.dbay.repository;

import lk.dbay.entity.item.ItemPackage;

import java.util.List;

public interface ItemPackageDAO {

    List<ItemPackage> getItemPackages(String type, String txt, String category, String district, String town);

}
