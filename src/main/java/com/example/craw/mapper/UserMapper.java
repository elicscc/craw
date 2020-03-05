package com.example.craw.mapper;


import com.example.craw.domain.User;

import java.util.List;

public interface UserMapper {
     User findByName(String username);
    User findById(Long id);

    List<User> findAll();

    Integer updateUser(User user);
}
