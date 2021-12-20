package lk.dbay.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lk.dbay.entity.DateTime;
import lk.dbay.entity.Item;
import lk.dbay.entity.ItemImg;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemImgDTO extends DateTimeDTO {

    private String itemImgId;
    private byte[] itemImg;
    private String itemImgName;
    private String itemImgType;
    private boolean thumbnail;

    private ItemDTO item;

    public ItemImgDTO(@NonNull ItemImg itemImg) {
        this.itemImgId = itemImg.getItemImgId();
        this.itemImg = itemImg.getItemImg();
        this.itemImgName = itemImg.getItemImgName();
        this.itemImgType = itemImg.getItemImgType();
        this.thumbnail = itemImg.isThumbnail();
    }
}
