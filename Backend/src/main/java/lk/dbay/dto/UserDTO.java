package lk.dbay.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDTO extends DateTimeDTO {

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
    private boolean active;
    private boolean verified;
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

//    public UserDTO(ClassUser user) {
//        if (user != null) {
//            this.userId = user.getUserId();
//            this.userTitle = user.getUserTitle();
//            this.email = user.getEmail();
//            this.firstName = user.getFirstName();
//            this.lastName = user.getLastName();
//            this.username = user.getUsername();
//            this.contactNumber = user.getContactNumber();
//            this.role = user.getRole();
//            this.active = user.isActive();
//            this.verified = user.isVerified();
//            this.verificationCode = user.getVerificationCode();
////            this.curDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
////            setCreatedAt(user.getCreatedAt());
////            setUpdatedAt(user.getUpdatedAt());
//        }
//    }

}
