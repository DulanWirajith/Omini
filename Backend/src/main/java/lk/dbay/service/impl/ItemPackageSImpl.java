package lk.dbay.service.impl;

import lk.dbay.dto.ItemDTO;
import lk.dbay.dto.ItemPackageDTO;
import lk.dbay.dto.ItemPackageImageDTO;
import lk.dbay.dto.OrderDetailDTO;
import lk.dbay.entity.BusinessProfileCategoryPK;
import lk.dbay.entity.item.ItemPackage;
import lk.dbay.entity.item.ItemPackageImage;
import lk.dbay.repository.ItemPackageImageR;
import lk.dbay.repository.ItemPackageR;
import lk.dbay.service.ItemPackageS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ItemPackageSImpl implements ItemPackageS {

    @Autowired
    private ItemPackageR itemPackageR;
    @Autowired
    private ItemPackageImageR itemPackageImageR;

    @Override
    public ItemPackageImageDTO getItemPackageImage(String id) {
        Optional<ItemPackageImage> itemImgOptional = itemPackageImageR.findById(id);
        if (itemImgOptional.isPresent()) {
            ItemPackageImage itemPackageImage = itemImgOptional.get();
            return new ItemPackageImageDTO(itemPackageImage);
        }
        return null;
    }

    @Override
    public boolean setItemPackageAvailable(String itemId) {
        Optional<ItemPackage> itemOptional = itemPackageR.findById(itemId);
        if (itemOptional.isPresent()) {
            ItemPackage itemPackage = itemOptional.get();
            itemPackage.setAvailable(!itemPackage.isAvailable());
            itemPackageR.save(itemPackage);
            return itemPackage.isAvailable();
        }
        return false;
    }

    @Override
    public List<ItemPackageDTO> getItemsPackagesBySearch(String txt, String category) {
//        List<ItemPackage> itemsBySearch;
        List<ItemPackage> itemPackagesBySearch;
        if (category.equals("no")) {
//            itemsBySearch = itemR.getItemsBySearch("%" + txt + "%");
            itemPackagesBySearch = itemPackageR.getItemPackagesBySearch("%" + txt + "%");
        } else {
//            itemsBySearch = itemR.getItemsBySearch("%" + txt + "%", category);
            itemPackagesBySearch = itemPackageR.getItemPackagesBySearch("%" + txt + "%", category);
        }
        List<ItemPackageDTO> itemPackageDTOS = new ArrayList<>();
        for (ItemPackage packagesBySearch : itemPackagesBySearch) {
            itemPackageDTOS.add(new ItemPackageDTO(packagesBySearch));
        }

        return itemPackageDTOS;
    }
}
