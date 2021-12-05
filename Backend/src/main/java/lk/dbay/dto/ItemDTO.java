package lk.dbay.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lk.dbay.entity.Item;
import lk.dbay.entity.Town;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemDTO extends DateTimeDTO {

    private String itemId;
    private String itemTitle;
    private int itemQty;
    private double itemPrice;
    private String itemDescription;
    private double itemDiscount;
    private String itemDiscountType;
    private ItemCategoryDTO itemCategory;

    public ItemDTO(@NonNull Item item) {
        this.itemId = item.getItemId();
        this.itemTitle = item.getItemTitle();
        this.itemQty = item.getItemQty();
        this.itemPrice = item.getItemPrice();
        this.itemDescription = item.getItemDescription();
        this.itemDiscount = item.getItemDiscount();
        this.itemDiscountType = item.getItemDiscountType();
    }

    public ItemDTO(@NonNull Item item, @NonNull ItemCategoryDTO itemCategory) {
        this(item);
        this.itemCategory = itemCategory;
    }
}
