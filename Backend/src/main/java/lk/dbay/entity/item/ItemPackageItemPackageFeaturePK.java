package lk.dbay.entity.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ItemPackageItemPackageFeaturePK implements Serializable {

    private String itemPackageId;
    private String itemPackageFeatureId;
}
