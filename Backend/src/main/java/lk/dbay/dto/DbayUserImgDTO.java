package lk.dbay.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lk.dbay.entity.DateTime;
import lk.dbay.entity.DbayUser;
import lk.dbay.entity.DbayUserImg;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DbayUserImgDTO extends DateTimeDTO {

    private String userImgId;

    //    @Lob
//    private byte[] itemImg;
    private String userImgName;
    private String userImgType;
    //    private String itemImgPath;
    private boolean thumbnail;

    public DbayUserImgDTO(DbayUserImg dbayUserImg) {
        if (dbayUserImg != null) {
            this.userImgId = dbayUserImg.getUserImgId();
            this.userImgName = dbayUserImg.getUserImgName();
            this.userImgType = dbayUserImg.getUserImgType();
            this.thumbnail = dbayUserImg.isThumbnail();
        }
    }
}
