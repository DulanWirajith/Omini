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
public class BusinessArea extends DateTime {

    @EmbeddedId
    private BusinessAreaPK businessAreaId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "businessProId", referencedColumnName = "businessProId", insertable = false, updatable = false, nullable = false)
    private BusinessProfile businessProfile;

    @ManyToOne(optional = false)
    @JoinColumn(name = "townId", referencedColumnName = "townId", insertable = false, updatable = false, nullable = false)
    private Town town;
}
