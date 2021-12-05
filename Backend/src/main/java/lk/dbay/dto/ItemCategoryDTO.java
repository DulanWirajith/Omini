package lk.dbay.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lk.dbay.entity.Item;
import lk.dbay.entity.ItemCategory;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemCategoryDTO extends DateTimeDTO {

    private String itemCategoryId;
    private String name;
    private String description;

    public ItemCategoryDTO(@NonNull ItemCategory itemCategory) {
        this.itemCategoryId = itemCategory.getItemCategoryId();
        this.name = itemCategory.getName();
        this.description = itemCategory.getDescription();
    }
}
