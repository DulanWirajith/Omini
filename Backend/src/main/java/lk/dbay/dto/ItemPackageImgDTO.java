package lk.dbay.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lk.dbay.entity.DateTime;
import lk.dbay.entity.ItemPackage;
import lk.dbay.entity.ItemPackageImg;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemPackageImgDTO extends DateTimeDTO {

    private String itemPackageImgId;
    private byte[] itemPackageImg;
    private String itemPackageImgName;
    private String itemPackageImgType;
    private boolean thumbnail;

    public ItemPackageImgDTO(ItemPackageImg itemPackageImg) {
        if (itemPackageImg != null) {
            this.itemPackageImgId = itemPackageImg.getItemPackageImgId();
            this.itemPackageImgName = itemPackageImg.getItemPackageImgName();
            this.itemPackageImgType = itemPackageImg.getItemPackageImgType();
            this.thumbnail = itemPackageImg.isThumbnail();
        }
    }
}
