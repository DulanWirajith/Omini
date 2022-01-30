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
    public List<ItemPackageDTO> getItemsPackageBusinessCategory(String businessProfileId, String businessCategoryId) {
        List<ItemPackage> itemList = itemPackageR.getAllByBusinessProfileCategory_BusinessProfileCategoryId(new BusinessProfileCategoryPK(businessProfileId, businessCategoryId));
        List<ItemPackageDTO> itemPackageDTOS = new ArrayList<>();
        for (ItemPackage itemPackage : itemList) {
            itemPackageDTOS.add(new ItemPackageDTO(itemPackage));
        }
        return itemPackageDTOS;
    }

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
    public List<ItemPackageDTO> getItemPackagesOrdered(String businessProfileId, String businessCategoryId, int start, int limit) {
        List<ItemPackage> itemPackageList = itemPackageR.getItemsPackageOrdered(new BusinessProfileCategoryPK(businessProfileId, businessCategoryId), PageRequest.of(start, limit));
        List<ItemPackageDTO> itemPackageDTOS = new ArrayList<>();
        for (ItemPackage itemPackage : itemPackageList) {
            ItemPackageDTO itemPackageDTO = new ItemPackageDTO(itemPackage);
            itemPackageDTO.setItemPackageImages(itemPackage);
            itemPackageDTOS.add(itemPackageDTO);
        }
        return itemPackageDTOS;
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
    public ItemPackageDTO getItemPackageSelected(String itemId) {
        Optional<ItemPackage> itemPackageOptional = itemPackageR.findById(itemId);
        if (itemPackageOptional.isPresent()) {
            ItemPackage itemPackage = itemPackageOptional.get();
//            List<ItemFeature> itemFeatureRAll = itemFeatureR.getAllByBusinessCategory_BusinessCategoryIdAndConfirmed(item.getBusinessProfileCategory().getBusinessCategory().getBusinessCategoryId(), true);
//            List<ItemFeatureDTO> itemFeatureDTOS = new ArrayList<>();
//            for (ItemFeature itemFeature : itemFeatureRAll) {
//                itemFeatureDTOS.add(new ItemFeatureDTO(itemFeature));
//            }
            ItemPackageDTO itemPackageDTO = new ItemPackageDTO(itemPackage);
            itemPackageDTO.setBusinessProfileCategory(itemPackage);
            itemPackageDTO.setItemPackageItemPackageFeatures(itemPackage);
//            itemDTO.setItemFeatures(item);
            if (itemPackage.getItemPackageType().equals("Item")) {
                itemPackageDTO.getItemDTO().setItemCategory(itemPackage.getItem());
            }
            itemPackageDTO.setOrderDetail(new OrderDetailDTO());
            return itemPackageDTO;
        }
        return null;
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
