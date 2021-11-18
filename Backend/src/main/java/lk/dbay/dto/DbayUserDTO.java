package lk.dbay.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lk.dbay.entity.DbayUser;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DbayUserDTO extends DateTimeDTO {

    private String token;
    private String userId;
    private String userTitle;
    private String firstName;
    private String lastName;
    private String username;
    private String oldPassword;
    private String password;
    private String contactNumber;
    private String email;
    private String nic;
    private String profilePic;
    private String address;
    private String role;
    private String type;
    private boolean available;
    private boolean confirmed;
    private DbayUser confirmedBy;
    private LocalDateTime confirmedAt;
    private boolean active;
    private String verificationCode;
    private int securityKey;
    private int editRow = 1;
    private String curDateTime;
    //    private List<UserPrivilegeDTO> userPrivileges;
    private List<String> privileges;

//    private InstituteDTO institute;
//    private StudentDTO student;
//    private TutorDTO tutor;
//    private AdminDTO admin;

    public DbayUserDTO(@NonNull DbayUser dbayUser) {
//        if (dbayUser != null) {
        this.userId = dbayUser.getUserId();
        this.email = dbayUser.getEmail();
//        this.firstName = dbayUser.getFirstName();
//        this.lastName = dbayUser.getLastName();
        this.username = dbayUser.getUsername();
        this.role = dbayUser.getRole();
        this.available = dbayUser.isAvailable();
        this.confirmed = dbayUser.isConfirmed();
        this.verificationCode = dbayUser.getVerificationCode();
//            this.curDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
//            setCreatedAt(dbayUser.getCreatedAt());
//            setUpdatedAt(dbayUser.getUpdatedAt());
//        }
    }

}
