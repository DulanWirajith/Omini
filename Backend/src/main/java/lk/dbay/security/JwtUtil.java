package lk.dbay.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lk.dbay.dto.DbayUserDTO;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;

@Component
public class JwtUtil {

    private String secret = "mercedes";
    private static final long JWT_TOKEN_VALIDITY = 60 * 60 * 18; //18 hours

    public String generate(DbayUserDTO user) {
        Claims claims = Jwts.claims().setSubject(user.getUsername());
        claims.put("userId", String.valueOf(user.getUserId()));
        claims.put("role", user.getRole());
        claims.put("securityKey", user.getSecurityKey());
//        claims.put("privileges", user.getPrivileges());
//        user.setPrivileges(null);
        claims.put("user", user);
//        System.out.println(user.getSecurityKey());
        claims.setExpiration(new Date(new Date().getTime() + JWT_TOKEN_VALIDITY * 1000));

        return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    public DbayUserDTO validate(String token) {
        try {
            Claims body = getBody(token);
            DbayUserDTO user = new DbayUserDTO();
            user.setUsername(body.getSubject());
            user.setUserId(body.get("userId").toString());
            user.setRole(body.get("role").toString());
            user.setPrivileges((ArrayList) body.get("privileges"));
            user.setSecurityKey(Integer.valueOf(body.get("securityKey").toString()));
            return user;
        } catch (ExpiredJwtException e) {
            throw e;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public int getSeurityCode(String token) {
        Claims body = getBody(token);
        return Integer.parseInt(body.get("securityKey").toString());
    }

    private Claims getBody(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }


    public boolean validateToken(DbayUserDTO user, JwtUserDetails userDetails, HttpServletRequest httpServletRequest) {
//        boolean rollIdentified = false;
//        if (httpServletRequest.getRequestURI().startsWith("/" + CommonConstants.DOMAIN_MEDILAB + CommonConstants.ADMIN) && user.getRole().equals("Admin")) {
//            rollIdentified = true;
//        } else if (httpServletRequest.getRequestURI().startsWith("/" + CommonConstants.DOMAIN_MEDILAB + CommonConstants.USER) && user.getRole().equals("User")) {
//            rollIdentified = true;
//        }
//        return user.getUserId().equals(userDetails.getUserId()) && user.getSecurityKey() == userDetails.getSecurityKey() && rollIdentified;

        boolean rollIdentified = false;
        if (httpServletRequest.getRequestURI().matches("^(/)[a-z_]+(/)[a-z_]+(/superadmin/).+$") && user.getRole().equals("SuperAdmin")) {
            rollIdentified = true;
        } else if (httpServletRequest.getRequestURI().matches("^(/)[a-z_]+(/)[a-z_]+(/admin/).+$") && (user.getRole().equals("Admin") || user.getRole().equals("SuperAdmin"))) {
            rollIdentified = true;
        } else if (httpServletRequest.getRequestURI().matches("^(/)[a-z_]+(/)[a-z_]+(/user/).+$") && (user.getRole().equals("User") || user.getRole().equals("Admin") || user.getRole().equals("SuperAdmin"))) {
            rollIdentified = true;
        }
        return user.getUserId().equals(userDetails.getUserId()) && user.getSecurityKey() == userDetails.getSecurityKey() && rollIdentified;
//        return user.getUserId().equals(userDetails.getUserId()) && user.getSecurityKey() == userDetails.getSecurityKey();
    }
}
