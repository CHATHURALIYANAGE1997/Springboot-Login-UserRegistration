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
    @Value("$(site.base.url.https)")
    private String emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender mailSender;

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
    
    public void updateResetPasswordToken(String token, String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user != null ) {
            user.setResetPasswordToken(token);
            userRepository.save(user);
        } else {
            throw new UsernameNotFoundException("Could not find any customer with the email " + email);
        }
    }
    
    public User getByResetPasswordToken(String token) {
        return userRepository.findByResetPasswordToken(token);
    }
    
    public void updatePassword(User customer, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        customer.setPassword(encodedPassword);

        customer.setResetPasswordToken(null);
        userRepository.save(customer);
    }
    
    
    public void processforgotPassword(String email, String token, HttpServletRequest request, Model model){
       try {
            updateResetPasswordToken(token, email);
            String resetPasswordLink = Utility.getSiteURL(request) + "/reset_password?token=" + token;
            System.out.println(email);
            sendEmail(email, resetPasswordLink);
            model.addAttribute("message", "We have sent a reset password link to your email. Please check.");

        }
        catch (UsernameNotFoundException ex) {
            model.addAttribute("error", ex.getMessage());
        } catch (UnsupportedEncodingException | MessagingException e) {
            model.addAttribute("error", "Error while sending email");
        }

    }


}
