package lk.dbay.service.impl;

import lk.dbay.dto.ItemCategoryDTO;
import lk.dbay.entity.ItemCategory;
import lk.dbay.repository.ItemCategoryR;
import lk.dbay.service.ItemCategoryS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class ItemCategorySImpl implements ItemCategoryS {

    @Autowired
    private ItemCategoryR itemCategoryR;

    @Override
    public ItemCategoryDTO addCategory(ItemCategory itemCategory) {
        LocalDateTime localDateTime = LocalDateTime.now();
        String format = localDateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        itemCategory.setItemCategoryId("ICA" + format);
        return new ItemCategoryDTO(itemCategoryR.save(itemCategory));
    }
}
