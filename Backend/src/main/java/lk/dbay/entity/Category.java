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
        @UniqueConstraint(columnNames = {"categoryName", "businessProId", "businessCategoryId"}, name = "CategoryName")
})
public class Category extends DateTime {

    @Id
    private String categoryId;
    private String categoryName;
    private String description;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "businessProId", referencedColumnName = "businessProId", updatable = false, nullable = false),
            @JoinColumn(name = "businessCategoryId", referencedColumnName = "businessCategoryId", updatable = false, nullable = false)
    })
    private BusinessProfileCategory businessProfileCategory;

}
