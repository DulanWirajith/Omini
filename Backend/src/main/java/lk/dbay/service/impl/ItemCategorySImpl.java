package lk.dbay.service.impl;

import lk.dbay.dto.ItemCategoryDTO;
import lk.dbay.entity.BusinessProfileCategory;
import lk.dbay.entity.BusinessProfileCategoryPK;
import lk.dbay.entity.Item;
import lk.dbay.entity.ItemCategory;
import lk.dbay.repository.ItemCategoryR;
import lk.dbay.service.ItemCategoryS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ItemCategorySImpl implements ItemCategoryS {

    @Autowired
    private ItemCategoryR itemCategoryR;

    @Override
    @Transactional
    public ItemCategoryDTO addCategory(ItemCategory itemCategory) throws Exception {
        try {
            LocalDateTime localDateTime = LocalDateTime.now();
            String format = localDateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            BusinessProfileCategory businessProfileCategory = itemCategory.getBusinessProfileCategory();
            itemCategory.setItemCategoryId("ICA" + format);
            businessProfileCategory.setBusinessProfileCategoryId(
                    new BusinessProfileCategoryPK(businessProfileCategory.getBusinessProfile().getBusinessProId(), businessProfileCategory.getBusinessCategory().getBusinessCategoryId())
            );
            addItemsToCategory(itemCategory);
            return new ItemCategoryDTO(itemCategoryR.save(itemCategory));
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Something went wrong");
        }
    }

    private void addItemsToCategory(ItemCategory itemCategory) {
        for (Item item : itemCategory.getItems()) {
            item.setItemCategory(itemCategory);
            item.setBusinessProfileCategory(itemCategory.getBusinessProfileCategory());
        }
    }

    @Override
    public List<ItemCategoryDTO> getCategoriesOrdered(String businessProfileId, String businessCategoryId, int start, int limit) {
        List<ItemCategory> categoryList = itemCategoryR.getCategoriesOrdered(new BusinessProfileCategoryPK(businessProfileId, businessCategoryId), PageRequest.of(start, limit));
        List<ItemCategoryDTO> itemCategoryDTOS = new ArrayList<>();
        for (ItemCategory itemCategory : categoryList) {
            itemCategoryDTOS.add(new ItemCategoryDTO(itemCategory));
        }
        return itemCategoryDTOS;
    }

    @Override
    public ItemCategoryDTO getCategorySelected(String categoryId) {
        Optional<ItemCategory> itemCategoryOptional = itemCategoryR.findById(categoryId);
        if (itemCategoryOptional.isPresent()) {
            ItemCategory itemCategory = itemCategoryOptional.get();
            return new ItemCategoryDTO(itemCategory, itemCategory.getItems());
        }
        return null;
    }
}
