package com.rejestr.egb.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
private UserDetailsServiceImp userDetailsServiceImp;


    @Bean
    public PasswordEncoder gePasswordEncoder(){
        return new BCryptPasswordEncoder(10);
    }


    @Autowired
    public SecurityConfig(UserDetailsServiceImp userDetailsServiceImp) {
        this.userDetailsServiceImp = userDetailsServiceImp;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/REJESTRY").hasRole("USER")
                .antMatchers("/REJESTRY/*").hasRole("USER")
                .antMatchers("/Admin-User").hasRole("ADMIN")
                .antMatchers("/Edytor").hasRole("ADMIN")
                .antMatchers("/Import-danych").hasRole("USER")
                .anyRequest().permitAll()
                .and()
                .formLogin().permitAll()
//                .successForwardUrl("REJESTRY")
                .and()
                .logout().permitAll();
        http.headers().frameOptions().sameOrigin();
        http.csrf().disable();

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceImp);
    }
}
