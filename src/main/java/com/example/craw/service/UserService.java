package com.example.craw.service;


import com.example.craw.domain.User;

import java.util.List;

public interface UserService {
    User findByName(String username);
     User findById(Long id);
     Integer updateUser(User user);
    List<User> findAll();
    void save(User user);
}
