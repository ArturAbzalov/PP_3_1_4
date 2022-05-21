package com.example.pp_3_1_2.controller;


import com.example.pp_3_1_2.model.Role;
import com.example.pp_3_1_2.model.User;
import com.example.pp_3_1_2.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@RequestMapping("/api")
@RestController
public class RestAdminController {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @GetMapping("/users")
    public List<User> getUsers(){
        return userServiceImpl.findAll();
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable Long id) {
        return userServiceImpl.findById(id);
    }

    @PostMapping("/users")
    public User addNewUsers(@RequestBody User user){
        userServiceImpl.saveUser(user);
        return user;
    }

    @RequestMapping(value = "/users",
            produces = "application/json",
            method=RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUser(@RequestBody User user){
//        User user1 = userServiceImpl.findById(user.getId());
//        user1.setUsername(user.getUsername());
//        user1.setFirst_name(user.getFirst_name());
//        user1.setAge(user.getAge());
//        user1.setEmail(user.getEmail());
//        if(user1.getPassword()!=null){
//            user1.setPassword(user.getPassword());
//        }
//        user1.setRoles(user.getRoles());
        userServiceImpl.saveUser(user);
    }

    @DeleteMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
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
    }
}
