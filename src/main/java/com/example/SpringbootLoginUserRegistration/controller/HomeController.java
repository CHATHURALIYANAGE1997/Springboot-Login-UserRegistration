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


    @GetMapping("/forgot_password")
    public String showForgotPasswordForm() {
        return "forgot_password_form";
    }

    @PostMapping("/forgot_password")
    public String processForgotPassword(HttpServletRequest request, Model model) {


        String email = request.getParameter("email");
        String token = RandomString.make(30);
        myUserService.processforgotPassword(email,token,request,model);

        return "forgot_password_form";
    }


    @GetMapping("/reset_password")
    public String showResetPasswordForm  (HttpServletRequest request, Model model) {
        if ( request.getParameter("token")!=null ) {


            User user = myUserService.getByResetPasswordToken(request.getParameter("token"));
            model.addAttribute("token", request.getParameter("token"));

            if (user == null) {
                model.addAttribute("message", "Invalid Token");
                
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
