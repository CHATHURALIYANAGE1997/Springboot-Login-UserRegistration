package com.example.SpringbootLoginUserRegistration.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.bind.annotation.CrossOrigin;


@CrossOrigin
@Configuration
@EnableWebSecurity
public class AppConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

//    public AppConfig (UserDetailsService userDetailsService){
//        super();
//        this.userDetailsService=userDetailsService;
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authPovider(){
        DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
        //provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
        return provider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {


//        http.authorizeRequests()
//                .antMatchers("/forgot-passowrd").permitAll();
//                //.antMatchers("/login").permitAll();

                http
                .csrf().disable()
                        .authorizeRequests().antMatchers("/reset_password/**").permitAll().and()
                 .authorizeRequests().antMatchers("/forgot_password").permitAll().and()
                .authorizeRequests().antMatchers("/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .and()
                .logout().invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/logout-success").permitAll();


//        http
//                .csrf().disable()
//                .authorizeRequests().antMatchers("/login").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin()
//                .loginPage("/login").permitAll()
//                .and()
//                .logout().invalidateHttpSession(true)
//                .clearAuthentication(true)
//                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                .logoutSuccessUrl("/logout-success").permitAll();

//        http.
//                csrf().disable().
//                sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).
//                and().
//                authorizeRequests().
//                antMatchers("/api/v1/signup").permitAll().
//                anyRequest().authenticated().
//                and().
//                anonymous().disable();
//        http.addFilterBefore(new AuthenticationFilter(authenticationManager()), BasicAuthenticationFilter.class);


    }

//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        super.configure(web);
//        web.ignoring().antMatchers("/forgot-passowrd");
//
//    }

}
