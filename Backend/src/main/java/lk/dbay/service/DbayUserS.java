package lk.dbay.service;

import lk.dbay.dto.DbayUserDTO;
import lk.dbay.entity.DbayUser;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface DbayUserS extends UserDetailsService {

    DbayUserDTO authenticate(DbayUser dbayUser) throws Exception;

    boolean signOut(String id) throws Exception;

    boolean forgotPassword(String email) throws Exception;

    boolean resetPassword(DbayUser dbayUser) throws Exception;

    boolean resetOldPassword(DbayUserDTO dbayUser) throws Exception;

    List<DbayUserDTO> getAllUsersByRole(String role);

    DbayUserDTO addClassUser(DbayUser dbayUser) throws Exception;

    DbayUserDTO updateClassUser(DbayUser dbayUser) throws Exception;

    boolean removeClassUser(String id) throws Exception;

    DbayUserDTO getUser(String userId);
}
