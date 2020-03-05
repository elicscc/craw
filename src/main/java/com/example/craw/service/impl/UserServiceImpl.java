package com.example.craw.service.impl;

import com.example.craw.domain.User;
import com.example.craw.mapper.UserMapper;
import com.example.craw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public User findByName(String username) {
        return userMapper.findByName(username);
    }

    @Override
    public User findById(Long id) {
        return userMapper.findById(id);
    }

    @Override
    public Integer updateUser(User user) {return  userMapper.updateUser(user);
    }

    @Override
    public List<User> findAll() {
        return userMapper.findAll();
    }
}
