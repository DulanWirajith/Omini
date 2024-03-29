package lk.dbay.service.impl;

import lk.dbay.dto.ItemCategoryDTO;
import lk.dbay.entity.BusinessProfileCategory;
import lk.dbay.entity.BusinessProfileCategoryPK;
import lk.dbay.entity.item.Item;
import lk.dbay.entity.item.ItemCategory;
import lk.dbay.repository.ItemCategoryR;
import lk.dbay.repository.ItemR;
import lk.dbay.service.ItemCategoryS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class ItemCategorySImpl implements ItemCategoryS {

    @Autowired
    private ItemCategoryR itemCategoryR;
    @Autowired
    private ItemR itemR;

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

    @Override
    @Transactional
    public ItemCategoryDTO updateCategory(ItemCategory itemCategory, String itemCategoryId) throws Exception {
        try {
            Optional<ItemCategory> itemCategoryOptional = itemCategoryR.findById(itemCategoryId);
            if (itemCategoryOptional.isPresent()) {
                ItemCategory itemCategoryObj = itemCategoryOptional.get();
                itemCategoryObj.setName(itemCategory.getName());
                itemCategoryObj.setDescription(itemCategory.getDescription());
                itemCategoryObj.setConfirmed(itemCategory.isConfirmed());
//            LocalDateTime localDateTime = LocalDateTime.now();
//            String format = localDateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
                BusinessProfileCategory businessProfileCategory = itemCategory.getBusinessProfileCategory();
//            itemCategory.setItemCategoryId("ICA" + format);
                businessProfileCategory.setBusinessProfileCategoryId(
                        new BusinessProfileCategoryPK(businessProfileCategory.getBusinessProfile().getBusinessProId(), businessProfileCategory.getBusinessCategory().getBusinessCategoryId())
                );

                HashSet<Item> items = new HashSet<>(itemCategory.getItems());
                items.retainAll(itemCategoryObj.getItems());
                Set<Item> itemsSetRemove = new HashSet<>(itemCategoryObj.getItems());
                itemsSetRemove.removeAll(items);
                Set<Item> itemsSetAdd = new HashSet<>(itemCategory.getItems());
                itemsSetAdd.removeAll(items);
                itemCategoryObj.setItems(itemsSetAdd);
                for (Item item : itemsSetRemove) {
                    item.setItemCategory(null);
                }
                itemCategoryObj.setItems(itemsSetAdd);
                addItemsToCategory(itemCategoryObj);
                itemCategoryObj.getItems().addAll(itemsSetRemove);

                return new ItemCategoryDTO(itemCategoryR.save(itemCategoryObj));
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Something went wrong");
        }
    }

    private void addItemsToCategory(ItemCategory itemCategory) {
        for (Item item : itemCategory.getItems()) {
            item.setItemCategory(itemCategory);
//            item.getItemPackage().setBusinessProfileCategory(itemCategory.getBusinessProfileCategory());
        }
    }

    @Override
    public List<ItemCategoryDTO> getItemCategoriesOrdered(String businessProfileId, String businessCategoryId) {
        List<ItemCategory> categoryList = itemCategoryR.getItemCategoriesOrdered(new BusinessProfileCategoryPK(businessProfileId, businessCategoryId));
        List<ItemCategoryDTO> itemCategoryDTOS = new ArrayList<>();
        for (ItemCategory itemCategory : categoryList) {
            itemCategoryDTOS.add(new ItemCategoryDTO(itemCategory));
        }
        return itemCategoryDTOS;
    }

    @Override
    public ItemCategoryDTO getItemCategorySelected(String categoryId) {
        Optional<ItemCategory> itemCategoryOptional = itemCategoryR.findById(categoryId);
        if (itemCategoryOptional.isPresent()) {
            ItemCategory itemCategory = itemCategoryOptional.get();
            ItemCategoryDTO itemCategoryDTO = new ItemCategoryDTO(itemCategory);
            itemCategoryDTO.setBusinessProfileCategory(itemCategory);
            itemCategoryDTO.setItems(itemCategory, true);
            return itemCategoryDTO;
        }
        return null;
    }

    @Override
    public boolean removeCategory(String categoryId) throws Exception {
        try {
            List<Item> items = itemR.getByItemCategory_ItemCategoryId(categoryId);
//            if (items.isPresent()) {
//                Item item = items.get();
//                item.setItemCategory(null);
//                itemR.save(item);
//            }
            for (Item item : items) {
                item.setItemCategory(null);
                itemR.save(item);
            }
            itemCategoryR.deleteById(categoryId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("You cannot remove this category, since it is used.");
        }
    }
}