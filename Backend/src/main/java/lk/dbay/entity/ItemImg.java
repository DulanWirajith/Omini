package lk.dbay.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ItemImg extends DateTime {

    @Id
    private String itemImgId;

//    @Lob
//    private byte[] itemImg;
    private String itemImgName;
    private String itemImgType;
//    private String itemImgPath;
    private boolean thumbnail;

    @ManyToOne
    private Item item;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemImg itemImg = (ItemImg) o;
        return Objects.equals(itemImgId, itemImg.itemImgId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemImgId);
    }
}
