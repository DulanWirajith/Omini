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
        @UniqueConstraint(columnNames = "contactNumber", name = "ContactNumber"),
        @UniqueConstraint(columnNames = "customerAddress", name = "CustomerAddress")
})
public class CustomerProfile extends DateTime {

    @Id
    private String customerProId;
    private String customerName;
    private String contactNumber;
    private String customerAddress;
    private String gender;

    @OneToOne(optional = false)
    private DbayUser dbayUser;
    @ManyToOne(optional = false)
    private Town town;
}
