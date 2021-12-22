package lk.dbay.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ItemPackageImg extends DateTime {

    @Id
    private String itemPackageImgId;

    @Lob
    private byte[] itemPackageImg;
    private String itemPackageImgName;
    private String itemPackageImgType;
    private boolean thumbnail;

    @ManyToOne
    private ItemPackage itemPackage;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemPackageImg itemPackageImg = (ItemPackageImg) o;
        return Objects.equals(itemPackageImgId, itemPackageImg.itemPackageImgId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemPackageImgId);
    }
}
