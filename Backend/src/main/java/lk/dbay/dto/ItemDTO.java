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
    private String name;
    private int itemQty;
    private double itemPrice;
    private String itemDescription;
    private double itemDiscount;
    private String itemDiscountType;
    private ItemCategoryDTO itemCategory;
    private boolean itemAvailable;

    private byte[] itemImg;
    private String itemImgName;
    private String itemImgType;

    public ItemDTO(@NonNull Item item, boolean needImage) {
        this.itemId = item.getItemId();
        this.itemTitle = item.getItemTitle();
        this.itemQty = item.getItemQty();
        this.itemPrice = item.getItemPrice();
        this.itemDescription = item.getItemDescription();
        this.itemDiscount = item.getItemDiscount();
        this.itemDiscountType = item.getItemDiscountType();
        this.itemAvailable = item.isItemAvailable();
        if (needImage) {
            this.itemImgName = item.getItemImgName();
            this.itemImg = item.getItemImg();
            this.itemImgType = item.getItemImgType();
        }
    }

    public ItemDTO(@NonNull Item item, boolean needImage, @NonNull ItemCategoryDTO itemCategory) {
        this(item, needImage);
        this.itemCategory = itemCategory;
    }
}
