package lk.dbay.service;

import lk.dbay.dto.ItemPackageDTO;
import lk.dbay.entity.ItemPackage;
import org.springframework.web.multipart.MultipartFile;

public interface ItemPackageS {

    ItemPackageDTO addPackage(ItemPackage itemPackage, MultipartFile file) throws Exception;

}
