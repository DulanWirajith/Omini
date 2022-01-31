package lk.dbay.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lk.dbay.entity.item.Item;
import lk.dbay.entity.item.ItemCategory;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemCategoryDTO extends DateTimeDTO {

    private String itemCategoryId;
    private String name;
    private String description;
    private BusinessProfileCategoryDTO businessProfileCategory;
    private List<ItemDTO> items;

    public ItemCategoryDTO(ItemCategory itemCategory) {
        if (itemCategory != null) {
            this.itemCategoryId = itemCategory.getItemCategoryId();
            this.name = itemCategory.getName();
            this.description = itemCategory.getDescription();
        }
    }

    public void setBusinessProfileCategory(ItemCategory itemCategory) {
        if (itemCategory.getBusinessProfileCategory() != null) {
            this.businessProfileCategory = new BusinessProfileCategoryDTO(itemCategory.getBusinessProfileCategory().getBusinessProfile(), itemCategory.getBusinessProfileCategory().getBusinessCategory());
        }
    }

    public void setItems(ItemCategory itemCategory, boolean needImage) {
        this.items = new ArrayList<>();
        for (Item item : itemCategory.getItems()) {
            ItemDTO itemDTO = new ItemDTO(item);
            itemDTO.setItemPackage(item);
            if (needImage) {
                itemDTO.getItemPackage().setItemPackageImages(item.getItemPackage());
            }
            this.items.add(itemDTO);
        }
    }

//    public ItemCategoryDTO(@NonNull ItemCategory itemCategory, @NonNull BusinessProfileCategory businessProfileCategory) {
//        this(itemCategory);
//        this.businessProfileCategory = new BusinessProfileCategoryDTO(businessProfileCategory.getBusinessProfile(), businessProfileCategory.getBusinessCategory());
//    }

//    public ItemCategoryDTO(@NonNull ItemCategory itemCategory, @NonNull BusinessProfileCategory businessProfileCategory, @NonNull Set<Item> items, boolean needImage) {
//        this(itemCategory, businessProfileCategory);
//        this.items = new ArrayList<>();
//        for (Item item : items) {
//            this.items.add(new ItemDTO(item, needImage));
//        }
//    }
}