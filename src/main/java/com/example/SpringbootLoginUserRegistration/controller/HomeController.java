package com.example.SpringbootLoginUserRegistration.controller;

import com.example.SpringbootLoginUserRegistration.model.User;
import com.example.SpringbootLoginUserRegistration.service.MyUserService;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin
@Controller
public class HomeController {


    @Autowired
    private MyUserService myUserService;


    @RequestMapping("/")
    public String home(){
        return "home";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/logout-success")
    public String logout(){
        return "login";
    }


    // private JavaMailSender mailSender;



    @GetMapping("/forgot_password")
    public String showForgotPasswordForm() {
        return "forgot_password_form";
    }

    @PostMapping("/forgot_password")
    public String processForgotPassword(HttpServletRequest request, Model model) {


        String email = request.getParameter("email");
        String token = RandomString.make(30);
        myUserService.processforgotPassword(email,token,request,model);
//
//        try {
//            myUserService.updateResetPasswordToken(token, email);
//            String resetPasswordLink = Utility.getSiteURL(request) + "/reset_password?token=" + token;
//
//            sendEmail(email, resetPasswordLink);
//            model.addAttribute("message", "We have sent a reset password link to your email. Please check.");
//
//        }
//        catch (UsernameNotFoundException ex) {
//            model.addAttribute("error", ex.getMessage());
//        } catch (UnsupportedEncodingException | MessagingException e) {
//            model.addAttribute("error", "Error while sending email");
//        }

        return "forgot_password_form";
    }

//    public void sendEmail(String recipientEmail, String link)
//            throws MessagingException, UnsupportedEncodingException {
//
//
//        SimpleMailMessage message=new SimpleMailMessage();
//        message.setFrom("chathurabimalka1997@gmail.com");
//        message.setTo(recipientEmail);
//
////        MimeMessage message = mailSender.createMimeMessage();
////        MimeMessageHelper helper = new MimeMessageHelper(message);
//
////        helper.setFrom("chathurabimalka1997@gmail.com", "Shopme Support");
////        helper.setTo(recipientEmail);
//
//        String subject = "Here's the link to reset your password";
//        message.setSubject(subject);
//        String content = "<p>Hello,</p>"
//                + "<p>You have requested to reset your password.</p>"
//                + "<p>Click the link below to change your password:</p>"
//                + "<p><a href=\"" + link + "\">Change my password</a></p>"
//                + "<br>"
//                + "<p>Ignore this email if you do remember your password, "
//                + "or you have not made the request.</p>";
//        message.setText(content);
////        helper.setSubject(subject);
////
////        helper.setText(content, true);
//        mailSender.send(message);
//    }


    @GetMapping("/reset_password")
    public String showResetPasswordForm  (HttpServletRequest request, Model model) {
        if ( request.getParameter("token")!=null ) {


            User user = myUserService.getByResetPasswordToken(request.getParameter("token"));
            model.addAttribute("token", request.getParameter("token"));

            if (user == null) {
                model.addAttribute("message", "Invalid Token");
                //throw new UsernameNotFoundException("Not valid one");
                return "message";
            }
        }
        else {
            return "forgot_password_form";
        }

        return "reset_password_form";
    }

    @PostMapping("/reset_password")
    public String processResetPassword(HttpServletRequest request, Model model) {
        String token = request.getParameter("token");
        String password = request.getParameter("password");

        User user = myUserService.getByResetPasswordToken(token);
        model.addAttribute("title", "Reset your password");

        if (user == null) {
            model.addAttribute("message", "Invalid Token");
            return "message";
        } else {
            myUserService.updatePassword(user, password);

            model.addAttribute("message", "You have successfully changed your password.");
        }
        return "/login";
    }


}
