package lk.dbay.service;

import lk.dbay.dto.PackageItemDTO;
import lk.dbay.entity.item.PackageItem;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PackageS {

    PackageItemDTO addPackage(PackageItem packageItem, MultipartFile[] file) throws Exception;

    PackageItemDTO updatePackage(PackageItem packageItem, MultipartFile[] files, String itemPackageId) throws Exception;

    List<PackageItemDTO> getPackageItemsOrdered(String businessProfileId, String businessCategoryId, int start, int limit);

//    PackageItemDTO getPackageItemSelected(String packageItemId);

    boolean removePackage(String packageItemId) throws Exception;
}