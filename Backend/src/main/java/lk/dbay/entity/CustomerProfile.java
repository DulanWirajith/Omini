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
        @UniqueConstraint(columnNames = {"contactNumber1", "contactNumber2"}, name = "ContactNumbers"),
        @UniqueConstraint(columnNames = "customerAddress", name = "CustomerAddress")
})
public class CustomerProfile extends DateTime {

    @Id
    private String customerProId;
    private String firstName;
    private String lastName;
    private String contactNumber1;
    private String contactNumber2;
    private String customerAddress;
    private String gender;

    @OneToOne(optional = false)
    private DbayUser dbayUser;
    @ManyToOne(optional = false)
    private Town town;
}
