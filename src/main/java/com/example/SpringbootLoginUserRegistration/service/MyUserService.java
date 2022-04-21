package com.example.SpringbootLoginUserRegistration.service;

import com.example.SpringbootLoginUserRegistration.model.User;
import com.example.SpringbootLoginUserRegistration.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MyUserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userRepository.findByUsername(username);
        if (user==null)
            throw new UsernameNotFoundException("User 404");
        return new UserPrinceple(user);
    }

    public User save(User user ){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
       userRepository.save(user);
        return user;
    }


}
