package com.example.pp_3_1_2.controller;

import com.example.pp_3_1_2.model.User;
import com.example.pp_3_1_2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String findAll(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users",users);
        return "userList";
    }

    @GetMapping("/user-create")
    public String createUserForm(User user) {
        return "userCreate";
    }

    @PostMapping("/userCreate")
    public String createUser(User user) {
        userService.saveUser(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/user-delete/{id}")
    public String deleteUser(@PathVariable("id")Long id) {
        userService.deleteById(id);
        return "redirect:/admin/users";
    }
    @GetMapping("/user-update/{id}")
    public String updateUserForm(@PathVariable("id")Long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user",user);
        return "userUpdate";
    }

    @PostMapping("/user-update")
    public String updateUser(User user) {
        User user1 = userService.findById(user.getId());
        user1.setUsername(user.getUsername());
        user1.setFirst_name(user.getFirst_name());
        userService.saveUser(user1);
        return "redirect:/admin/users";
    }
}
