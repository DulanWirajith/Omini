package lk.dbay.service.impl;

import lk.dbay.dto.DbayUserDTO;
import lk.dbay.entity.DbayUser;
import lk.dbay.repository.DbayUserR;
import lk.dbay.security.JwtCache;
import lk.dbay.security.JwtUtil;
import lk.dbay.service.DbayUserService;
import lk.dbay.service.SendEmailSMTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class DbayUserServiceImpl implements DbayUserService {

    @Value("${email.link}")
    private String emailLink;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private SendEmailSMTP sendEmailSMTP;
    @Autowired
    private JwtUtil jwtGenerator;
    @Autowired
    private JwtCache jwtCache;
    @Autowired
    private DbayUserR dbayUserR;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        DbayUser userByUsernameOrEmail = dbayUserR.getUserByUsernameOrEmailAndVerificationCode(s);
        List<SimpleGrantedAuthority> roles = new ArrayList<>();
//        for (UserPrivilege userPrivilege : userByUsernameOrEmail.getUserPrivileges()) {
//            roles.add(new SimpleGrantedAuthority(userPrivilege.getPrivilege().getPrivilegeName()));
//        }
        return new org.springframework.security.core.userdetails.User(userByUsernameOrEmail.getUsername(), userByUsernameOrEmail.getPassword(), roles);
    }

    @Override
    public DbayUserDTO authenticate(DbayUser dbayUser) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dbayUser.getUsername(), dbayUser.getPassword()));
            DbayUser userObj = dbayUserR.getUserByUsernameOrEmailAndVerificationCode(dbayUser.getUsername());
            if (userObj != null) {
//                if (userObj.getUserId().startsWith("AT")) {
//
//                } else if (userObj.getUserId().startsWith("A")) {
//
//                } else if (userObj.getUserId().startsWith("T")) {
//
//                } else if (userObj.getUserId().startsWith("S")) {
//
//                }
//                System.out.println(userObj.getPassword());
                userObj.setCreatedAt(null);
//                userObj.getPharmacy().setCreatedAt(null);
                userObj.setUpdatedAt(null);
//                userObj.getPharmacy().setUpdatedAt(null);
                DbayUserDTO userDTOobj = new DbayUserDTO(userObj);
                userDTOobj.setSecurityKey(dbayUser.hashCode());

//                List<String> userPrivileges = new ArrayList<>();
//                for (UserPrivilege userPrivilege : userObj.getUserPrivileges()) {
//                    userPrivileges.add(userPrivilege.getPrivilege().getPrivilegeName());
//                }
//                userDTOobj.setPrivileges(userPrivileges);

                userDTOobj.setToken(jwtGenerator.generate(userDTOobj));

                jwtCache.setUser(userDTOobj);

                DbayUserDTO userDTO = new DbayUserDTO();
//                userDTO.setCurDateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
                userDTO.setToken(userDTOobj.getToken());
                userDTO.setUserId(userDTOobj.getUserId());
                userDTO.setUsername(userDTOobj.getUsername());
                userDTO.setFirstName(userDTOobj.getFirstName());
                userDTO.setLastName(userDTOobj.getLastName());
                userDTO.setRole(userDTOobj.getRole());
                userDTO.setSecurityKey(0);

                return userDTO;
            }
        } catch (BadCredentialsException e) {
            e.printStackTrace();
            throw new Exception("Bad Credentials");
        } catch (InternalAuthenticationServiceException e) {
            e.printStackTrace();
            throw new Exception("User cannot be found");
        }
        return null;
    }

    @Override
    public boolean signOut(String id) throws Exception {
        DbayUserDTO userDTO = new DbayUserDTO();
        userDTO.setUserId(id + "");
        DbayUserDTO user = jwtCache.getUser(userDTO);
        if (user != null) {
            return jwtCache.removeUser(user);
        }
        return false;
    }

    @Override
    public boolean forgotPassword(String email) throws Exception {
        DbayUser userByEmail = dbayUserR.findByEmail(email);
        if (userByEmail != null) {
            String verifyCode = (new Random().nextInt(89999999) + 10000001) + "";
            sendEmailSMTP.sendEmail(email, "YEWOI(Dbay) Password Reset", emailLink + "?email=" + email + "&verify=" + verifyCode);
            userByEmail.setVerificationCode(verifyCode);
            dbayUserR.save(userByEmail);
            return true;
        } else {
            throw new Exception("You are not a registered user");
        }
    }

    @Override
    public boolean resetPassword(DbayUser user) throws Exception {
        DbayUser userByEmail = dbayUserR.findByEmail(user.getEmail());
        if (userByEmail != null && user.getVerificationCode() != null && !user.getVerificationCode().equals("") && userByEmail.getVerificationCode().equals(user.getVerificationCode())) {
            userByEmail.setVerificationCode(null);
            userByEmail.setPassword(user.getPassword());
            dbayUserR.save(userByEmail);
            return true;
        } else {
            throw new Exception("Failed");
        }
    }

    @Override
    public boolean resetOldPassword(DbayUserDTO user) throws Exception {
        Optional<DbayUser> userOptional = dbayUserR.findById(user.getUserId());
        if (userOptional.isPresent()) {
            DbayUser userObj = userOptional.get();
            if (user.getOldPassword().equals(userObj.getPassword())) {
                userObj.setVerificationCode(null);
                userObj.setPassword(user.getPassword());
                dbayUserR.save(userObj);
                return true;
            } else {
                throw new Exception("Incorrect old password");
            }
        } else {
            throw new Exception("User not found");
        }
    }

    @Override
    public List<DbayUserDTO> getAllUsersByRole(String role) {
        return null;
    }

    @Override
    public DbayUserDTO addClassUser(DbayUser user) throws Exception {
        try {
            String verifyCode = (new Random().nextInt(89999999) + 10000001) + "";
            user.setVerificationCode(verifyCode);
            dbayUserR.save(user);
            String emailTxt = "Hello " + user.getFirstName() + " " + user.getLastName() + ", " +
                    "\nYou are now new user to the system" +
                    "\nUse below link to set your password\n\n" +
                    "\n" + emailLink + "?email=" + user.getEmail() + "&verify=" + verifyCode;
            sendEmailSMTP.sendEmail(user.getEmail(), "YEWOI(Dbay) New " + user.getRole(), emailTxt);
//            System.out.println(123);
            return new DbayUserDTO(user);
        } catch (DataIntegrityViolationException ex) {
            ex.printStackTrace();
            throw new Exception(ex.getCause().getCause().getMessage().split("'")[3].replace('_', ' ') + " is already taken, Try again");
        }
    }

    @Override
    public DbayUserDTO updateClassUser(DbayUser user) throws Exception {
        try {
            Optional<DbayUser> userOptional = dbayUserR.findById(user.getUserId());
            if (userOptional.isPresent()) {
                DbayUser userObj = userOptional.get();
                userObj.setFirstName(user.getFirstName());
                userObj.setLastName(user.getLastName());
                userObj.setUsername(user.getUsername());
                userObj.setEmail(user.getEmail());
                userObj.setRole(user.getRole());
                dbayUserR.save(userObj);
                return new DbayUserDTO(userObj);
            }
            return null;
        } catch (DataIntegrityViolationException ex) {
            ex.printStackTrace();
            throw new Exception(ex.getCause().getCause().getMessage().split("'")[3].replace('_', ' ') + " is already taken, Try again");
        }
    }

    @Override
    public boolean removeClassUser(String id) throws Exception {
        Optional<DbayUser> userOptional = dbayUserR.findById(id);
        if (userOptional.isPresent()) {
            dbayUserR.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public DbayUserDTO getUser(String userId) {
        return null;
    }
}
