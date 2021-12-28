package lk.dbay.service.impl;

import lk.dbay.dto.ItemCategoryDTO;
import lk.dbay.dto.ItemPackageDTO;
import lk.dbay.entity.*;
import lk.dbay.repository.ItemItemPackageR;
import lk.dbay.repository.ItemPackageImgR;
import lk.dbay.repository.ItemPackageR;
import lk.dbay.service.ItemPackageS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class ItemPackageSImpl implements ItemPackageS {

    @Autowired
    private ItemPackageR itemPackageR;
    @Autowired
    private ItemItemPackageR itemItemPackageR;
    @Autowired
    private ItemPackageImgR itemPackageImgR;

    @Override
    @Transactional
    public ItemPackageDTO addPackage(ItemPackage itemPackage, MultipartFile[] files) throws Exception {
        try {
            LocalDateTime localDateTime = LocalDateTime.now();
            String format = localDateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            BusinessProfileCategory businessProfileCategory = itemPackage.getBusinessProfileCategory();
            itemPackage.setItemPackageId("IPK" + businessProfileCategory.getBusinessProfile().getBusinessProId() + format);
            addItemsToItemPackage(itemPackage);
            addImagesToItemPackage(itemPackage, files);
            businessProfileCategory.setBusinessProfileCategoryId(
                    new BusinessProfileCategoryPK(businessProfileCategory.getBusinessProfile().getBusinessProId(), businessProfileCategory.getBusinessCategory().getBusinessCategoryId())
            );
            itemPackageR.save(itemPackage);
            return new ItemPackageDTO(itemPackage, itemPackage.getBusinessProfileCategory(), itemPackage.getItemPackageImgs());
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Something went wrong");
        }
    }

    @Override
    @Transactional
    public ItemPackageDTO updatePackage(ItemPackage itemPackage, MultipartFile[] files, String itemPackageId) throws Exception {
        try {
            Optional<ItemPackage> itemPackageOptional = itemPackageR.findById(itemPackageId);
            if (itemPackageOptional.isPresent()) {
                ItemPackage itemPackageObj = itemPackageOptional.get();
                itemPackageObj.setName(itemPackage.getName());
                itemPackageObj.setDescription(itemPackage.getDescription());
                itemPackageObj.setPrice(itemPackage.getPrice());
                itemPackageObj.setDiscount(itemPackage.getDiscount());
                itemPackageObj.setDiscountType(itemPackage.getDiscountType());
                itemPackageObj.setConfirmed(itemPackage.isConfirmed());
//                addItemsToItemPackage(itemPackage);
//                addImagesToItemPackage(itemPackage, files);
                BusinessProfileCategory businessProfileCategory = itemPackage.getBusinessProfileCategory();
                businessProfileCategory.setBusinessProfileCategoryId(
                        new BusinessProfileCategoryPK(businessProfileCategory.getBusinessProfile().getBusinessProId(), businessProfileCategory.getBusinessCategory().getBusinessCategoryId())
                );

                HashSet<ItemItemPackage> itemItemPackages = new HashSet<>(itemPackage.getItemItemPackages());
                itemItemPackages.retainAll(itemPackageObj.getItemItemPackages());
                Set<ItemItemPackage> itemItemPackagesSetRemove = new HashSet<>(itemPackageObj.getItemItemPackages());
                itemItemPackagesSetRemove.removeAll(itemItemPackages);
                Set<ItemItemPackage> itemItemPackagesSetAdd = new HashSet<>(itemPackage.getItemItemPackages());
                itemItemPackagesSetAdd.removeAll(itemItemPackages);
                itemPackageObj.setItemItemPackages(itemItemPackagesSetAdd);
                addItemsToItemPackage(itemPackageObj);

                HashSet<ItemPackageImg> itemPackageImgs = new HashSet<>(itemPackage.getItemPackageImgs());
                itemPackageImgs.retainAll(itemPackageObj.getItemPackageImgs());
                Set<ItemPackageImg> itemPackageImgsSetRemove = new HashSet<>(itemPackageObj.getItemPackageImgs());
                itemPackageImgsSetRemove.removeAll(itemPackageImgs);
                Set<ItemPackageImg> itemPackageImgsSetAdd = new HashSet<>(itemPackage.getItemPackageImgs());
                itemPackageImgsSetAdd.removeAll(itemPackageImgs);
                itemPackageObj.setItemPackageImgs(itemPackageImgsSetAdd);
                addImagesToItemPackage(itemPackageObj, files);

                if (itemItemPackagesSetRemove.size() > 0) {
                    itemItemPackageR.deleteAll(itemItemPackagesSetRemove);
                }
                if (itemPackageImgsSetRemove.size() > 0) {
                    itemPackageImgR.deleteAll(itemPackageImgsSetRemove);
                }

                itemPackageR.save(itemPackageObj);
                return new ItemPackageDTO(itemPackageObj, itemPackageObj.getBusinessProfileCategory(), itemPackageObj.getItemPackageImgs());
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Something went wrong");
        }
    }

    private void addItemsToItemPackage(ItemPackage itemPackage) {
        for (ItemItemPackage itemItemPackage : itemPackage.getItemItemPackages()) {
            itemItemPackage.setItemItemPackageId(new ItemItemPackagePK(itemItemPackage.getItem().getItemId(), itemPackage.getItemPackageId()));
            itemItemPackage.setItemPackage(itemPackage);
        }
    }

    private void addImagesToItemPackage(ItemPackage itemPackage, MultipartFile[] files) {
        try {
//            Set<ItemImg> itemImgs = new HashSet<>();
            for (ItemPackageImg itemPackageImg : itemPackage.getItemPackageImgs()) {
                itemPackageImg.setItemPackage(itemPackage);
            }
            LocalDateTime localDateTime = LocalDateTime.now();
            String format = localDateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            int i = 0;
            for (MultipartFile file : files) {
                ItemPackageImg itemPackageImg = new ItemPackageImg();
                itemPackageImg.setItemPackageImgId("ITPKGIMG" + ++i + format);
                itemPackageImg.setItemPackageImg(file.getBytes());
                itemPackageImg.setItemPackageImgName(StringUtils.cleanPath(file.getOriginalFilename()));
                itemPackageImg.setItemPackageImgType(file.getContentType());
                itemPackageImg.setItemPackage(itemPackage);
                itemPackage.getItemPackageImgs().add(itemPackageImg);
            }
//            itemPackage.setItemImgs(itemImgs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<ItemPackageDTO> getItemPackagesOrdered(String businessProfileId, String businessCategoryId) {
        List<ItemPackage> itemPackageList = itemPackageR.getItemPackagesOrdered(new BusinessProfileCategoryPK(businessProfileId, businessCategoryId));
        List<ItemPackageDTO> itemPackageDTOS = new ArrayList<>();
        for (ItemPackage itemPackage : itemPackageList) {
            itemPackageDTOS.add(new ItemPackageDTO(itemPackage, itemPackage.getBusinessProfileCategory(), itemPackage.getItemPackageImgs()));
        }
        return itemPackageDTOS;
    }

    @Override
    public ItemPackageDTO getItemPackageSelected(String itemPackageId) {
        Optional<ItemPackage> itemPackageOptional = itemPackageR.findById(itemPackageId);
        if (itemPackageOptional.isPresent()) {
            ItemPackage itemPackage = itemPackageOptional.get();
            return new ItemPackageDTO(itemPackage, itemPackage.getBusinessProfileCategory(), itemPackage.getItemItemPackages(), itemPackage.getItemPackageImgs(), true);
        }
        return null;
    }

//    private void addItemsToPackage(ItemPackage itemPackage) {
//        itemPackage.geti
//    }
}
