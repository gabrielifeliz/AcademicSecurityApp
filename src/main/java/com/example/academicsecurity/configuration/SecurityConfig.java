package com.example.academicsecurity.configuration;

import com.example.academicsecurity.model.SSUserDetailsService;
import com.example.academicsecurity.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    AppUserRepository userRepository;

    @Override
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return new SSUserDetailsService(userRepository);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/css/**","/img/**","/js/**").permitAll()
                .antMatchers("/").hasAnyAuthority("STUDENT", "TEACHER")
                .antMatchers("/studentorteacher").hasAnyAuthority("STUDENT", "TEACHER")
                .anyRequest()
                .authenticated()
                .and()
                .formLogin().permitAll()
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder pwEncoder = new BCryptPasswordEncoder();

        auth.inMemoryAuthentication().withUser("student").password(pwEncoder.encode("pwstudent")).authorities("STUDENT")
                .and()
                .withUser("teacher").password(pwEncoder.encode("pwteacher")).authorities("TEACHER")
                .and()
                .passwordEncoder(pwEncoder);
        auth.userDetailsService(userDetailsServiceBean()).passwordEncoder(pwEncoder);
    }
}