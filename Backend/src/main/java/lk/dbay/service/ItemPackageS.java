package lk.dbay.service;

import lk.dbay.dto.ItemCategoryDTO;
import lk.dbay.dto.ItemPackageDTO;
import lk.dbay.entity.ItemPackage;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ItemPackageS {

    ItemPackageDTO addPackage(ItemPackage itemPackage, MultipartFile[] file) throws Exception;

    ItemPackageDTO updatePackage(ItemPackage itemPackage, MultipartFile[] files, String itemPackageId) throws Exception;

    List<ItemPackageDTO> getItemPackagesOrdered(String businessProfileId, String businessCategoryId);

    ItemPackageDTO getItemPackageSelected(String itemPackageId);
}
