package lk.dbay.security;

import lk.dbay.service.DbayUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

//    @Autowired
//    private JwtAuthenticationProvider authenticationProvider;

    @Autowired
    private JwtAuthenticationEntryPoint entryPoint;

//    @Autowired
//    private UserDetailsService userDetailsService;

    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private DbayUserService userService;

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService);
//    }
//
//    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }

    //    @Bean
//    public AuthenticationManager authenticationManager() {
//        return new ProviderManager(Collections.singletonList(authenticationProvider));
//    }
//
//    @Bean
//    public JwtAuthenticationTokenFilter authenticationTokenFilter() {
//        JwtAuthenticationTokenFilter tokenFilter = new JwtAuthenticationTokenFilter();
//        tokenFilter.setAuthenticationManager(authenticationManager());
//        tokenFilter.setAuthenticationSuccessHandler(new JwtSuccessHandler());
//        return tokenFilter;
//    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests().antMatchers( "/**").permitAll()//test
//                .authorizeRequests().antMatchers("/" + CommonConstants.DOMAIN_ECLASS + "/pharmacy_user/authenticate").permitAll()
//                .and()
//                .authorizeRequests().antMatchers("/" + CommonConstants.DOMAIN_ECLASS + "/pharmacy_user/curDateTime").permitAll()
//                .and()
//                .authorizeRequests().antMatchers("/" + CommonConstants.DOMAIN_ECLASS + "/pharmacy_user/signup").permitAll()
//                .and()
//                .authorizeRequests().antMatchers("/" + CommonConstants.DOMAIN_ECLASS + "/pharmacy_user/forgotPassword/**").permitAll()
//                .and()
//                .authorizeRequests().antMatchers("/" + CommonConstants.DOMAIN_ECLASS + "/pharmacy_user/resetPassword").permitAll()
//                .and()
//                .authorizeRequests().antMatchers("/" + CommonConstants.DOMAIN_ECLASS + "/pharmacy_user/user/signout/**").permitAll()
//                .and()
//                .authorizeRequests().antMatchers("/" + CommonConstants.DOMAIN_ECLASS + "**").authenticated()
//                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(entryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//                .and()
//                .httpBasic();

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
//        http.headers().cacheControl();
        http.headers().disable();
        http.cors();
    }
}
