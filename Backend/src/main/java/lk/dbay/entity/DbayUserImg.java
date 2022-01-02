package lk.dbay.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DbayUserImg extends DateTime {

    @Id
    private String userImgId;

    //    @Lob
//    private byte[] itemImg;
    private String userImgName;
    private String userImgType;
    //    private String itemImgPath;
    private boolean thumbnail;

    @ManyToOne
    private DbayUser dbayUser;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DbayUserImg dbayUserImg = (DbayUserImg) o;
        return Objects.equals(userImgId, dbayUserImg.userImgId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userImgId);
    }
}
