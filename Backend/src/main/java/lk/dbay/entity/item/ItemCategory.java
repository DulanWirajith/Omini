package lk.dbay.entity.item;

import lk.dbay.entity.BusinessProfileCategory;
import lk.dbay.entity.DateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name", "businessProId", "businessCategoryId"}, name = "ItemCategoryName")
})
public class ItemCategory extends DateTime {

    @Id
    private String itemCategoryId;
    private String name;
    //    @Lob
    @Column(columnDefinition = "TEXT")
    private String description;
    private boolean confirmed;

    @ManyToOne(optional = false)
    @JoinColumns({
            @JoinColumn(name = "businessProId", referencedColumnName = "businessProId", updatable = false, nullable = false),
            @JoinColumn(name = "businessCategoryId", referencedColumnName = "businessCategoryId", updatable = false, nullable = false)
    })
    private BusinessProfileCategory businessProfileCategory;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "itemCategory")
    private Set<Item> items;
}
