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
        @UniqueConstraint(columnNames = "email", name = "Email_Address")
})
public class User extends DateTime {

    @Id
    private String userId;
    private String name;
    private String type;
    private String password;
    private String verificationCode;
    private String email;
    private boolean available;
    private boolean confirmed;
}
