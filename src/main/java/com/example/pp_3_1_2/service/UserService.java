package com.example.pp_3_1_2.service;

import com.example.pp_3_1_2.model.User;

import java.util.List;

public interface UserService {
    User findById(Long id);
    List<User> findAll();
    User saveUser(User user);
    User findByEmail(String email);
    void deleteById(Long id);
    User findByName(String username);
}
