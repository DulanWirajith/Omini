package lk.dbay.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = "name", name = "BusinessCategoryName")
})
public class BusinessCategory extends DateTime {

    @Id
    private String businessCategoryId;
    private String name;
}
