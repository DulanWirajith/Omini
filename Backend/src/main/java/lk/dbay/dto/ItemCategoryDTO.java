package lk.dbay.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lk.dbay.entity.BusinessProfileCategory;
import lk.dbay.entity.Item;
import lk.dbay.entity.ItemCategory;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

    public ItemCategoryDTO(@NonNull ItemCategory itemCategory) {
        this.itemCategoryId = itemCategory.getItemCategoryId();
        this.name = itemCategory.getName();
        this.description = itemCategory.getDescription();
    }

    public ItemCategoryDTO(@NonNull ItemCategory itemCategory, @NonNull BusinessProfileCategory businessProfileCategory) {
        this(itemCategory);
        this.businessProfileCategory = new BusinessProfileCategoryDTO(businessProfileCategory.getBusinessProfile(), businessProfileCategory.getBusinessCategory());
    }

    public ItemCategoryDTO(@NonNull ItemCategory itemCategory, @NonNull BusinessProfileCategory businessProfileCategory, @NonNull Set<Item> items, boolean needImage) {
        this(itemCategory, businessProfileCategory);
        this.items = new ArrayList<>();
        for (Item item : items) {
            this.items.add(new ItemDTO(item, needImage));
        }
    }
}
