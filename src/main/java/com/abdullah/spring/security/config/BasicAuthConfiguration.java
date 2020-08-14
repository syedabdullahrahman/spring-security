package com.abdullah.spring.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static com.abdullah.spring.security.config.ApplicationUserPermission.COURSE_WRITE;
import static com.abdullah.spring.security.config.ApplicationUserRole.*;

@Configuration
@EnableWebSecurity
public class BasicAuthConfiguration extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;


    @Autowired
    public BasicAuthConfiguration(PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
    }

/*
    */
/**
     * This is for Basic Auth
     * Return HTTP 401 status if not authorized
     * Applies through out any http request
     * @param http
     * @throws Exception
     *//*


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }
*/

    /**
     * White Listed some URLs by antMatchers() and permitAll()
     * @param http
     * @throws Exception
     */

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()       // very important to protect api
                .authorizeRequests()
                .antMatchers("/", "index", "/css/*", "/js/*").permitAll()  // This URLs can be accessed by all
                .antMatchers("/api/**").hasRole(STUDENT.name())     //This URLs needs Role Based permissions
                .antMatchers(HttpMethod.DELETE, "/management/api/**").hasAuthority(COURSE_WRITE.name())
                .antMatchers(HttpMethod.POST, "/management/api/**").hasAuthority(COURSE_WRITE.name())
                .antMatchers(HttpMethod.PUT, "/management/api/**").hasAuthority(COURSE_WRITE.name())
                .antMatchers(HttpMethod.GET, "/management/api/**").hasAnyRole(ADMIN.name(), TRAINEE_ADMIN.name())
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    /*    */

    /**
     * Basic auth user login per new new request
     *
     * @return
     *//*
    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails anna = User.builder()
                .username("anna")
                .password(passwordEncoder.encode("a1"))
                .roles("STUDENT")
                .build();

        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin123"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(anna,admin);

    }*/

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails anna = User.builder()
                .username("anna")
                .password(passwordEncoder.encode("a1"))
                .roles(STUDENT.name())  // ROLE_STUDENT
                .build();

        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin123"))
                .roles(ADMIN.name())  //ROLE_ADMIN
                .build();

        UserDetails traineeAdmin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin123"))
                .roles(TRAINEE_ADMIN.name())  // ROLE_TRAINEE_ADMIN
                .build();

        return new InMemoryUserDetailsManager(anna, admin, traineeAdmin);

    }
}
