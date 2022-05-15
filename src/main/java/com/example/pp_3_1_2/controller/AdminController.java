package com.example.pp_3_1_2.controller;

import com.example.pp_3_1_2.model.Role;
import com.example.pp_3_1_2.model.User;
import com.example.pp_3_1_2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;


    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String findAll(Model model, Principal principal) {
        List<User> users = userService.findAll();
        User user = userService.findByEmail(principal.getName());
        model.addAttribute("users",users);
        model.addAttribute("user",user);
        model.addAttribute("newUser",new User());
        return "adminPanel";
    }

    @PostMapping("/userCreate")
    public String createUser(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/admin/users";
    }


    @PostMapping("/user-update")
    public String updateUser(User user) {
        System.out.println(user+"userupdate");
        User user1 = userService.findById(user.getId());
        user1.setUsername(user.getUsername());
        user1.setFirst_name(user.getFirst_name());
        user1.setAge(user.getAge());
        user1.setEmail(user.getEmail());
        user1.setPassword(user1.getPassword());
        user1.setRoles(user.getRoles());
        userService.saveUser(user1);
        return "redirect:/admin/users";
    }

    @GetMapping("/findOne")
    @ResponseBody
    public User findOne(Long id) {
        System.out.println(userService.findById(id));
        return userService.findById(id);
    }


    @GetMapping("/delete")
    public String deleteUser(long id) {
        User user = userService.findById(id);
        if(Objects.nonNull(user)){
            List<Role> posts = user.getRoles();
            for (Iterator<Role> iterator = posts.iterator(); iterator.hasNext();) {
                Role role = iterator.next();
                role.setUsers(null);
                iterator.remove(); //remove the child first
            }
            userService.deleteById(id);
        }
        return "redirect:/admin/users";
    }
}
