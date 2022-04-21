package com.example.SpringbootLoginUserRegistration.service;

import com.example.SpringbootLoginUserRegistration.model.Role;
import com.example.SpringbootLoginUserRegistration.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.*;


public class UserPrinceple implements UserDetails {
    private User user;

    public UserPrinceple(User user) {
        super();
        this.user=user;
    }

//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//
//        return Collections.singleton(new SimpleGrantedAuthority("USER"));
//    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Role> roles = user.getRoles();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }


    //    public boolean hasRole(String roleName) {
//        return this.user.hasRole(roleName);
//    }
    @Override
    public String getPassword() {

        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
