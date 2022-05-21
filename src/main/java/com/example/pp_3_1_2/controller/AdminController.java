package com.example.pp_3_1_2.controller;

import com.example.pp_3_1_2.model.Role;
import com.example.pp_3_1_2.model.User;
import com.example.pp_3_1_2.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserServiceImpl userServiceImpl;

    @Autowired
    public AdminController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @GetMapping("/users")
    public String findAll(Model model, Principal principal) {
        List<User> users = userServiceImpl.findAll();
        User user = userServiceImpl.findByEmail(principal.getName());
        model.addAttribute("users",users);
        model.addAttribute("user",user);
        model.addAttribute("newUser",new User());
        return "adminPanel";
    }

    @PostMapping("/userCreate")
    public String createUser(@ModelAttribute("user") User user) {
        userServiceImpl.saveUser(user);
        return "redirect:/admin/users";
    }


    @PostMapping("/user-update")
    public String updateUser(User user) {
        userServiceImpl.saveUser(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/findOne")
    @ResponseBody
    public User findOne(Long id) {
        System.out.println(userServiceImpl.findById(id));
        return userServiceImpl.findById(id);
    }


    @GetMapping("/delete")
    public String deleteUser(long id) {
        User user = userServiceImpl.findById(id);
        if(Objects.nonNull(user)){
            List<Role> posts = user.getRoles();
            for (Iterator<Role> iterator = posts.iterator(); iterator.hasNext();) {
                Role role = iterator.next();
                role.setUsers(null);
                iterator.remove(); //remove the child first
            }
            userServiceImpl.deleteById(id);
        }
        return "redirect:/admin/users";
    }


}
