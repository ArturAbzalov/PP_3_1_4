package com.example.pp_3_1_2.controller;

import com.example.pp_3_1_2.model.User;
import com.example.pp_3_1_2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;


@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/info")
    public String getUser(Model model, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        model.addAttribute("user",user);
        return "userPanel";
    }
}
