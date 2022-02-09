package lk.dbay.controller;

import lk.dbay.dto.DbayUserDTO;
import lk.dbay.entity.DbayUser;
import lk.dbay.service.DbayUserS;
import lk.dbay.util.CommonConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@CrossOrigin
@RestController
@RequestMapping(value = CommonConstants.DOMAIN_DBAY + CommonConstants.DBAY_USER)
public class DbayUserController {

    @Autowired
    private DbayUserS dbayUserService;

    @PostMapping(value = "/authenticate")
    public ResponseEntity authenticate(@RequestBody DbayUser dbayUser, HttpServletRequest request) {
        try {
//            @SuppressWarnings("unchecked")
//            List<String> messages = (List<String>) request.getSession().getAttribute("MY_SESSION_MESSAGES");
//            if (messages == null) {
//                messages = new ArrayList<>();
//                request.getSession().setAttribute("MY_SESSION_MESSAGES", messages);
//            }
//            messages.add("asd123");
//            request.getSession().setAttribute("MY_SESSION_MESSAGES", messages);
//            HttpSession session = request.getSession();
//            System.out.println(session);

            DbayUserDTO authenticate = dbayUserService.authenticate(dbayUser);
            if (authenticate != null) {
                return ResponseEntity.ok(authenticate);
            } else {
                return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/signout/{id}")
    public ResponseEntity signOut(@PathVariable String id, HttpSession session) {
//        System.out.println(session);
////        @SuppressWarnings("unchecked")
//        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");
//        for (String message : messages) {
//            System.out.println(message);
//        }
//
//        if (messages == null) {
//            messages = new ArrayList<>();
//        }

        try {
            if (dbayUserService.signOut(id)) {
                return ResponseEntity.ok("{\"reply\":\"Logout Success\"}");
            } else {
                return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/forgotPassword/{email}")
    public ResponseEntity forgotPassword(@PathVariable String email) {
        try {
            if (dbayUserService.forgotPassword(email)) {
                return ResponseEntity.ok("{\"reply\":\"Success\"}");
            } else {
                return new ResponseEntity<>("Email not found", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/resetPassword")
    public ResponseEntity resetPassword(@RequestBody DbayUser dbayUser) {
        try {
            if (dbayUserService.resetPassword(dbayUser)) {
                return ResponseEntity.ok("{\"reply\":\"Success\"}");
            } else {
                return new ResponseEntity<>("Failed", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = CommonConstants.USER + "/resetOldPassword")
    public ResponseEntity resetOldPassword(@RequestBody DbayUserDTO dbayUser) {
        try {
            if (dbayUserService.resetOldPassword(dbayUser)) {
                return ResponseEntity.ok("{\"reply\":\"Success\"}");
            } else {
                return new ResponseEntity<>("Failed", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/sendVerification/{email}")
    public ResponseEntity sendVerificationToEmail(@PathVariable String email) {
        try {
            DbayUserDTO dbayUserDTO = dbayUserService.sendVerificationToEmail(email);
            if (dbayUserDTO != null) {
                return ResponseEntity.ok(dbayUserDTO);
            } else {
                return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
            }
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(e.getCause().getCause().getMessage().split("'")[3].replace('_', ' ') + " is already taken, Try again", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/getNavBar/{userId}/{businessCategory}/{userType}")
    public ResponseEntity getNavBar(@PathVariable String userId, @PathVariable String businessCategory, @PathVariable String userType) {
        return ResponseEntity.ok(dbayUserService.getNavBar(userId, businessCategory, userType));
    }

//    @PostMapping(value = CommonConstants.ADMIN + "/addUser")
//    public ResponseEntity addClassUser(@RequestBody DbayUser dbayUser) {
//        try {
//            DbayUserDTO userDTO = dbayUserService.addClassUser(dbayUser);
//            if (userDTO != null) {
//                return ResponseEntity.ok(userDTO);
//            } else {
//                return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }

//    @PostMapping(value = CommonConstants.ADMIN + "/updateUser")
//    public ResponseEntity updateClassUser(@RequestBody DbayUser dbayUser) {
//        try {
//            DbayUserDTO userDTO = dbayUserService.updateClassUser(dbayUser);
//            if (userDTO != null) {
//                return ResponseEntity.ok(userDTO);
//            } else {
//                return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }

//    @DeleteMapping(value = CommonConstants.ADMIN + "/removeUser/{id}")
//    public ResponseEntity removeClassUser(@PathVariable String id) {
//        try {
//            if (dbayUserService.removeClassUser(id)) {
//                return ResponseEntity.ok("{\"reply\":\"Success\"}");
//            } else {
//                return new ResponseEntity<>("Failed", HttpStatus.BAD_REQUEST);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }

//    @GetMapping(value = CommonConstants.ADMIN + "/getAllUsers/{role}")
//    public ResponseEntity getAllUsersByRole(@PathVariable String role) {
//        return ResponseEntity.ok(dbayUserService.getAllUsersByRole(role));
//    }

//    @GetMapping(value = CommonConstants.ADMIN + "/getAllUsers")
//    public ResponseEntity getAllUsers() {
//        return ResponseEntity.ok(dbayUserService.getAllUsers());
//    }

//    @GetMapping(value = CommonConstants.ADMIN + "/getUser/{userId}")
//    public ResponseEntity getUser(@PathVariable String userId) {
//        return ResponseEntity.ok(dbayUserService.getUser(userId));
//    }
}
