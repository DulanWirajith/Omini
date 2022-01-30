package lk.dbay.entity.item;

import lk.dbay.entity.DateTime;
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
public class ItemPackageImage extends DateTime {

    @Id
    private String itemPackageImageId;

    private String imageName;
    private String imageType;
    private boolean thumbnail;

    @ManyToOne
    private ItemPackage itemPackage;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemPackageImage image = (ItemPackageImage) o;
        return Objects.equals(itemPackageImageId, image.itemPackageImageId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemPackageImageId);
    }
}
