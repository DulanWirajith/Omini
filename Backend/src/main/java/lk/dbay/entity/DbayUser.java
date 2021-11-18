package lk.dbay.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = "email", name = "Email_Address")
})
public class DbayUser extends DateTime {

    @Id
    private String userId;
    private String username;
    private String type;
    private String password;
    private String verificationCode;
//    private String firstName;
//    private String lastName;
    private String role;
    private String email;
    private boolean available;
    private boolean confirmed;

    @ManyToOne(optional = false)
    private DbayUser confirmedBy;
    private LocalDateTime confirmedAt;
}
