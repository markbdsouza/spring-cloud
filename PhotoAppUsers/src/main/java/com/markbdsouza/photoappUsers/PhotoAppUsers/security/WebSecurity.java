package com.markbdsouza.photoappUsers.PhotoAppUsers.security;

import com.markbdsouza.photoappUsers.PhotoAppUsers.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;
    @Autowired
    private Environment environment;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Value("${gateway.ip}" )
    private String ipAddress;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
//        //only allow http from any url with prefix users
        http.authorizeRequests().antMatchers("/**")
                .permitAll()
                .and()
                .addFilter(getAuthenticationFilter());
        http.headers().frameOptions().disable(); // to allow h2 console url to be accessible

//        http.csrf().disable();
//        http.authorizeRequests()
//                .antMatchers(HttpMethod.POST, "/**" )
//                .access("hasIpAddress(\"192.168.0.0/16\") or hasIpAddress(\"127.0.0.0/16\")" )
//                .antMatchers("/users/actuator/**").permitAll()
//                .and()
//                .addFilter(getAuthenticationFilter());

        http.headers().frameOptions().disable(); // to allow h2 console url to be accessible


    }

    private AuthenticationFilter getAuthenticationFilter() throws Exception {
        //register our authentication filter class
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(userService, environment);
        authenticationFilter.setAuthenticationManager(authenticationManager());
        authenticationFilter.setFilterProcessesUrl("/users/login");
        return authenticationFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
    }
}
