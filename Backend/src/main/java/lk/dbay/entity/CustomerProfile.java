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
        @UniqueConstraint(columnNames = "address", name = "Address")
})
public class CustomerProfile extends DateTime {

    @Id
    private String customerProId;
    private String customerName;
    private String contactNumber;
    private String address;
    private String gender;

    @OneToOne
    private User user;
    @OneToOne
    private Town town;
}
