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
public class BusinessArea extends DateTime {

    @EmbeddedId
    private BusinessAreaPK businessAreaId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "businessProId", referencedColumnName = "businessProId", insertable = false, updatable = false, nullable = false)
    private BusinessProfile businessProfile;

    @ManyToOne(optional = false)
    @JoinColumn(name = "townId", referencedColumnName = "townId", insertable = false, updatable = false, nullable = false)
    private Town town;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BusinessArea that = (BusinessArea) o;
        return Objects.equals(businessProfile.getBusinessProId(), that.businessProfile.getBusinessProId()) &&
                Objects.equals(town.getTownId(), that.town.getTownId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(businessProfile.getBusinessProId(), town.getTownId());
    }

    //    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        BusinessArea that = (BusinessArea) o;
//        return Objects.equals(town.getTownId(), that.town.getTownId());
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(town.getTownId());
//    }
}
