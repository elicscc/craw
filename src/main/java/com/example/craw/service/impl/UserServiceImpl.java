package com.example.craw.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.craw.domain.User;
import com.example.craw.mapper.UserMapper;
import com.example.craw.service.UserService;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;


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

    @Override
    public void save(User entity) {
//1.参数校验
        if(entity==null)
            throw new IllegalArgumentException("保存对象不能为空");
        if(StringUtils.isEmpty(entity.getUsername()))
            throw new IllegalArgumentException("用户名不能为空");
        if(StringUtils.isEmpty(entity.getPassword()))
            throw new IllegalArgumentException("密码不能为空");
//        if(roleIds==null||roleIds.length==0)
//            throw new IllegalArgumentException("必须为用户分配角色");
        //...
        //2.对密码进行加密

        String salt= UUID.randomUUID().toString();
        SimpleHash sh=new SimpleHash(
                "MD5",//algorithmName加密算法
                entity.getPassword(),//source 被加密的对象
                salt, //salt 盐值
                1);//hashIterations 加密
        entity.setSalt(salt);
        entity.setPassword(sh.toHex());
        //3.保存用户自身信息
        int rows=userMapper.insert(entity);
        //4.保存用户与角色关系数据
       // sysUserRoleDao.insertObjects(entity.getId(), roleIds);


    }
}
