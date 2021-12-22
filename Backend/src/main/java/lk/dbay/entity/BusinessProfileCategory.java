package lk.dbay.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BusinessProfileCategory extends DateTime {

    @EmbeddedId
    private BusinessProfileCategoryPK businessProfileCategoryId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "businessProId", referencedColumnName = "businessProId", insertable = false, updatable = false, nullable = false)
    private BusinessProfile businessProfile;

    @ManyToOne(optional = false)
    @JoinColumn(name = "businessCategoryId", referencedColumnName = "businessCategoryId", insertable = false, updatable = false, nullable = false)
    private BusinessCategory businessCategory;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BusinessProfileCategory that = (BusinessProfileCategory) o;
        return Objects.equals(businessProfile.getBusinessProId(), that.businessProfile.getBusinessProId()) &&
                Objects.equals(businessCategory.getBusinessCategoryId(), that.businessCategory.getBusinessCategoryId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(businessProfile.getBusinessProId(), businessCategory.getBusinessCategoryId());
    }

    //    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        BusinessProfileCategory that = (BusinessProfileCategory) o;
//        return Objects.equals(businessCategory.getBusinessCategoryId(), that.businessCategory.getBusinessCategoryId());
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(businessCategory.getBusinessCategoryId());
//    }
}
