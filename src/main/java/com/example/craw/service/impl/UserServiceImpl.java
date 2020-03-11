package com.example.craw.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
        QueryWrapper<User> queryWrapper =  new QueryWrapper<>();
        queryWrapper.eq("username",username);
        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public User findById(Long id) {
        return userMapper.selectById(id);
    }

    @Override
    public Integer updateUser(User user) {return  userMapper.update(user,null);
    }

    @Override
    public List<User> findAll() {
        return userMapper.selectList(null);
    }
}
