package com.example.SpringbootLoginUserRegistration.controller;

import com.example.SpringbootLoginUserRegistration.model.User;
import com.example.SpringbootLoginUserRegistration.service.MyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class UserRegistration {

    @Autowired
    private MyUserService myUserService;

    @ModelAttribute("user")
    public User user() {
        return new User();
    }

    @RequestMapping("/adduser")
    public String adduser(){
        return "adduser";
    }

    @PostMapping("/adduser")
    public String newuerdata(@ModelAttribute("user") User user){
        myUserService.save(user);
        return "redirect:/";
    }

}