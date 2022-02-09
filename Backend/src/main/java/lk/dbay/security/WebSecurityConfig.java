package lk.dbay.security;

import lk.dbay.service.DbayUserS;
import lk.dbay.util.CommonConstants;
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
    private DbayUserS userService;

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
        String urlPrefix = "/" + CommonConstants.DOMAIN_DBAY;
        http.csrf().disable()
//                .authorizeRequests().antMatchers( "/**").permitAll()//test
                .authorizeRequests().antMatchers(urlPrefix + CommonConstants.DBAY_USER + "/authenticate").permitAll()
                .and()
                .authorizeRequests().antMatchers(urlPrefix + CommonConstants.DBAY_USER + "/sendVerification/**").permitAll()
                .and()
                .authorizeRequests().antMatchers(urlPrefix + CommonConstants.BUSINESS_AREA + "/getBusinessAreas").permitAll()
                .and()
                .authorizeRequests().antMatchers(urlPrefix + CommonConstants.BUSINESS_CATEGORY + "/getBusinessCategories").permitAll()
                .and()
                .authorizeRequests().antMatchers(urlPrefix + CommonConstants.BUSINESS_PROFILE + "/addBusinessProfile").permitAll()
                .and()
                .authorizeRequests().antMatchers(urlPrefix + CommonConstants.BUSINESS_PROFILE + "/getBusinessProfile/**").permitAll()
                .and()
                .authorizeRequests().antMatchers(urlPrefix + CommonConstants.BUSINESS_PROFILE + "/getItemsBusinessProfile/**").permitAll()
                .and()
                .authorizeRequests().antMatchers(urlPrefix + CommonConstants.BUSINESS_PROFILE + "/getBusinessReviews/**").permitAll()
                .and()
                .authorizeRequests().antMatchers(urlPrefix + CommonConstants.CUSTOMER_PROFILE + "/addCustomerProfile").permitAll()
                .and()
                .authorizeRequests().antMatchers(urlPrefix + CommonConstants.ITEM_PACKAGE + "/getItemsPackagesBySearch/**").permitAll()
                .and()
                .authorizeRequests().antMatchers(urlPrefix + CommonConstants.ITEM_PACKAGE + "/getItemPackageSelected/**").permitAll()
                .and()
                .authorizeRequests().antMatchers(urlPrefix + CommonConstants.ITEM_PACKAGE + "/getItemPackageReviews/**").permitAll()
                .and()
                .authorizeRequests().antMatchers(urlPrefix + CommonConstants.TOWN + "/getCountries").permitAll()
                .and()
                .authorizeRequests().antMatchers(urlPrefix + CommonConstants.TOWN + "/getDistricts/**").permitAll()
                .and()
                .authorizeRequests().antMatchers(urlPrefix + CommonConstants.TOWN + "/getTowns/**").permitAll()
                .and()
                .authorizeRequests().antMatchers(urlPrefix + CommonConstants.TOWN + "/getTownsWIthDistrict").permitAll()
                .and()
                .authorizeRequests().antMatchers(urlPrefix + CommonConstants.DBAY_USER + "/getNavBar/**").permitAll()
                .and()
                .authorizeRequests().antMatchers(urlPrefix + "**").authenticated()
                .anyRequest().authenticated()
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
