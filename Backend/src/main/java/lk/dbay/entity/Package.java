package lk.dbay.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"packageName", "businessProId", "businessCategoryId"}, name = "PackageName")
})
public class Package extends DateTime {

    @Id
    private String packageId;
    private String packageName;
    private String packageDescription;
    private double packagePrice;
    private double packageDiscount;
    private String packageDiscountType;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "businessProId", referencedColumnName = "businessProId", updatable = false, nullable = false),
            @JoinColumn(name = "businessCategoryId", referencedColumnName = "businessCategoryId", updatable = false, nullable = false)
    })
    private BusinessProfileCategory businessProfileCategory;
}
