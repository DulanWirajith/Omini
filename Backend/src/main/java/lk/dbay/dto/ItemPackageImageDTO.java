package lk.dbay.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lk.dbay.entity.item.ItemPackageImage;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemPackageImageDTO extends DateTimeDTO {

    private String itemPackageImageId;

    private String imageName;
    private String imageType;
    private boolean thumbnail;

//    private ItemDTO item;

    public ItemPackageImageDTO(ItemPackageImage itemPackageImage) {
        if (itemPackageImage != null) {
            this.itemPackageImageId = itemPackageImage.getItemPackageImageId();
//        this.itemImgPath = itemImg.getItemImgPath();
            this.imageName = itemPackageImage.getImageName();
            this.imageType = itemPackageImage.getImageType();
            this.thumbnail = itemPackageImage.isThumbnail();
        }
    }
}