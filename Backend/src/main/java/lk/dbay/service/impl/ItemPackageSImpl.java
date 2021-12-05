package lk.dbay.service.impl;

import lk.dbay.dto.ItemPackageDTO;
import lk.dbay.entity.ItemPackage;
import lk.dbay.repository.ItemPackageR;
import lk.dbay.service.ItemPackageS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class ItemPackageSImpl implements ItemPackageS {

    @Autowired
    private ItemPackageR itemPackageR;

    @Override
    public ItemPackageDTO addPackage(ItemPackage itemPackage) {
        LocalDateTime localDateTime = LocalDateTime.now();
        String format = localDateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        itemPackage.setItemPackageId("IPK" + format);
        return new ItemPackageDTO(itemPackageR.save(itemPackage));
    }

}
