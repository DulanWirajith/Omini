package lk.dbay.service.impl;

import lk.dbay.dto.BusinessProfileDTO;
import lk.dbay.dto.CustomerProfileDTO;
import lk.dbay.dto.DbayUserDTO;
import lk.dbay.dto.ItemOrderDTO;
import lk.dbay.entity.*;
import lk.dbay.repository.BusinessProfileR;
import lk.dbay.repository.CustomerProfileR;
import lk.dbay.repository.DbayUserR;
import lk.dbay.repository.OrderDetailR;
import lk.dbay.security.JwtCache;
import lk.dbay.security.JwtUtil;
import lk.dbay.service.DbayUserS;
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

import java.util.*;

@Service
public class DbayUserSImpl implements DbayUserS {

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
    private DbayUser userByUsernameOrEmail;
    @Autowired
    private BusinessProfileR businessProfileR;
    @Autowired
    private CustomerProfileR customerProfileR;
    @Autowired
    private OrderDetailR orderDetailR;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        userByUsernameOrEmail = dbayUserR.getUserByUsernameOrEmailAndVerificationCode(s);
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
//            DbayUser userObj = dbayUserR.getUserByUsernameOrEmailAndVerificationCode(dbayUser.getUsername());
            if (userByUsernameOrEmail != null) {
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
                userByUsernameOrEmail.setCreatedAt(null);
//                userObj.getPharmacy().setCreatedAt(null);
                userByUsernameOrEmail.setUpdatedAt(null);
//                userObj.getPharmacy().setUpdatedAt(null);
                DbayUserDTO userDTOobj = new DbayUserDTO(userByUsernameOrEmail);
//                userDTOobj.setSecurityKey(dbayUser.hashCode());

//                List<String> userPrivileges = new ArrayList<>();
//                for (UserPrivilege userPrivilege : userObj.getUserPrivileges()) {
//                    userPrivileges.add(userPrivilege.getPrivilege().getPrivilegeName());
//                }
//                userDTOobj.setPrivileges(userPrivileges);

                userDTOobj.setToken(jwtGenerator.generate(userDTOobj));

//                jwtCache.setUser(userDTOobj);

//                DbayUserDTO userDTO = new DbayUserDTO();
//                userDTO.setCurDateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
//                userDTO.setToken(userDTOobj.getToken());
//                userDTO.setUserId(userDTOobj.getUserId());
//                userDTO.setUsername(userDTOobj.getUsername());
//                userDTO.setFirstName(userDTOobj.getFirstName());
//                userDTO.setLastName(userDTOobj.getLastName());
//                userDTO.setRole(userDTOobj.getRole());
//                userDTO.setSecurityKey(0);

                if (userDTOobj.getRole().equals("B")) {
                    Optional<BusinessProfile> businessProfileOptional = businessProfileR.findById(userDTOobj.getUserId());
                    if (businessProfileOptional.isPresent()) {
                        BusinessProfile businessProfile = businessProfileOptional.get();
                        userDTOobj.setBusinessProfile(setBusiness(businessProfile));
                    }
                } else if (userDTOobj.getRole().equals("C")) {
                    Optional<CustomerProfile> customerProfileOptional = customerProfileR.findById(userDTOobj.getUserId());
                    if (customerProfileOptional.isPresent()) {
                        CustomerProfile customerProfile = customerProfileOptional.get();
                        userDTOobj.setCustomerProfile(setCustomer(customerProfile));
                    }
                }

                return userDTOobj;
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

    private BusinessProfileDTO setBusiness(BusinessProfile businessProfile) {
        BusinessProfileDTO businessProfileDTO = new BusinessProfileDTO(businessProfile);
//        businessProfileDTO.setDbayUser(businessProfile, true);
        businessProfileDTO.setBusinessAreas(businessProfile);
        businessProfileDTO.setBusinessProfileCategories(businessProfile);
        businessProfileDTO.setTown(businessProfile);
        List<OrderDetail> itemOrderDetailsItems = orderDetailR.getItemOrderDetails(new BusinessProfileCategoryPK(businessProfile.getBusinessProId(), businessProfile.getDefaultBusiness().getBusinessCategoryId()), "Pending");
        Set<ItemOrderDTO> itemOrderDTOS = new HashSet<>();
        for (OrderDetail itemOrderDetail : itemOrderDetailsItems) {
            itemOrderDTOS.add(new ItemOrderDTO(itemOrderDetail.getItemOrder()));
        }
        businessProfileDTO.setCountPendingOrders(itemOrderDTOS.size());
        return businessProfileDTO;
//        userDTO.setBusinessProfile(businessProfileDTO);
    }

    private CustomerProfileDTO setCustomer(CustomerProfile customerProfile) {
        CustomerProfileDTO customerProfileDTO = new CustomerProfileDTO(customerProfile);
//        customerProfileDTO.setDbayUser(customerProfile, true);
        return customerProfileDTO;
    }

    @Override
    public boolean signOut(String id) throws Exception {
        DbayUserDTO userDTO = new DbayUserDTO();
        userDTO.setUserId(id + "");
        DbayUserDTO dbayUser = jwtCache.getUser(userDTO);
        if (dbayUser != null) {
            return jwtCache.removeUser(dbayUser);
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
    public boolean resetPassword(DbayUser dbayUser) throws Exception {
        DbayUser userByEmail = dbayUserR.findByEmail(dbayUser.getEmail());
        if (userByEmail != null && dbayUser.getVerificationCode() != null && !dbayUser.getVerificationCode().equals("") && userByEmail.getVerificationCode().equals(dbayUser.getVerificationCode())) {
            userByEmail.setVerificationCode(null);
            userByEmail.setPassword(dbayUser.getPassword());
            dbayUserR.save(userByEmail);
            return true;
        } else {
            throw new Exception("Failed");
        }
    }

    @Override
    public boolean resetOldPassword(DbayUserDTO dbayUser) throws Exception {
        Optional<DbayUser> userOptional = dbayUserR.findById(dbayUser.getUserId());
        if (userOptional.isPresent()) {
            DbayUser userObj = userOptional.get();
            if (dbayUser.getOldPassword().equals(userObj.getPassword())) {
                userObj.setVerificationCode(null);
                userObj.setPassword(dbayUser.getPassword());
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
    public DbayUserDTO addClassUser(DbayUser dbayUser) throws Exception {
        try {
            String verifyCode = (new Random().nextInt(89999999) + 10000001) + "";
            dbayUser.setVerificationCode(verifyCode);
            dbayUserR.save(dbayUser);
            String emailTxt = "Hello " + dbayUser.getUsername() + ", " +
                    "\nYou are now new user to the system" +
                    "\nUse below link to set your password\n\n" +
                    "\n" + emailLink + "?email=" + dbayUser.getEmail() + "&verify=" + verifyCode;
            sendEmailSMTP.sendEmail(dbayUser.getEmail(), "YEWOI(Dbay) New " + dbayUser.getRole(), emailTxt);
//            System.out.println(123);
            return new DbayUserDTO(dbayUser);
        } catch (DataIntegrityViolationException ex) {
            ex.printStackTrace();
            throw new Exception(ex.getCause().getCause().getMessage().split("'")[3].replace('_', ' ') + " is already taken, Try again");
        }
    }

    @Override
    public DbayUserDTO updateClassUser(DbayUser dbayUser) throws Exception {
        try {
            Optional<DbayUser> userOptional = dbayUserR.findById(dbayUser.getUserId());
            if (userOptional.isPresent()) {
                DbayUser userObj = userOptional.get();
//                userObj.setFirstName(dbayUser.getFirstName());
//                userObj.setLastName(dbayUser.getLastName());
                userObj.setUsername(dbayUser.getUsername());
                userObj.setEmail(dbayUser.getEmail());
                userObj.setRole(dbayUser.getRole());
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
