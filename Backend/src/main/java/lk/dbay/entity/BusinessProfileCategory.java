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
public class BusinessProfileCategory extends DateTime {

    @EmbeddedId
    private BusinessProfileCategoryPK businessProfileCategoryId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "businessProId", referencedColumnName = "businessProId", insertable = false, updatable = false, nullable = false)
    private BusinessProfile businessProfile;

    @ManyToOne(optional = false)
    @JoinColumn(name = "businessCategoryId", referencedColumnName = "businessCategoryId", insertable = false, updatable = false, nullable = false)
    private BusinessCategory businessCategory;
}
