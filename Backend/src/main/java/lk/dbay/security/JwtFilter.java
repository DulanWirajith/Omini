package lk.dbay.security;

import io.jsonwebtoken.ExpiredJwtException;
import lk.dbay.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private JwtCache jwtCache;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException, RuntimeException {

        String authorizationHeader = httpServletRequest.getHeader("Authorization");
        String token = null;
        UserDTO user = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);
            try {
                user = jwtUtil.validate(token);
//				username = jwtTokenUtil.getUsernameFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                System.out.println("Unable to get JWT Token");
            } catch (ExpiredJwtException e) {
                System.out.println("JWT Token has expired");
            } catch (Exception e) {
                System.out.println("Invalid");
            }
        } else {
            logger.warn("JWT Token does not begin with Bearer String");
        }

        if (user != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDTO userDTO = new UserDTO();
            userDTO.setUserId(user.getUserId());
            UserDTO userCached = jwtCache.getUser(userDTO);
            JwtUserDetails userDetails = null;
            if (userCached != null) {
                if (user.getSecurityKey() > 0) {
                    List<GrantedAuthority> roles = new ArrayList<>();
                    for (String privilege : user.getPrivileges()) {
                        roles.add(new SimpleGrantedAuthority(privilege));
                    }
                    userDetails = new JwtUserDetails(userCached.getSecurityKey(), userCached.getUserId(), roles);
                }else{
                    throw new RuntimeException("JWT Token has expired");
                }
            }

            if (userDetails != null && jwtUtil.validateToken(user, userDetails, httpServletRequest)) {

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
