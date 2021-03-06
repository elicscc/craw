package com.example.craw.config;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.craw.controller.BaseController;
import com.example.craw.domain.User;
import com.example.craw.mapper.UserMapper;
import com.example.craw.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class UserRealm extends AuthorizingRealm {
    @Autowired
    private UserMapper userMapper;

    /**
     * 加密算法
     * @param credentialsMatcher
     */
    @Override
    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
        HashedCredentialsMatcher matcher=new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("MD5");
        matcher.setHashIterations(1);
        super.setCredentialsMatcher(matcher);
    }

    /**
     * 认证
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //System.err.println("开始认证");
        /**
         *  获取用户提交的身份信息
         *  基于身份信息查询数据库用户
         *  判断用户是否存在
         *  判断用户是否被禁用
         *  封装用户信息并返回
         */
        UsernamePasswordToken uToken = (UsernamePasswordToken) authenticationToken;
        String username = uToken.getUsername();
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("username",username);
        User user = userMapper.selectOne(queryWrapper);
        if (null==user){
            throw  new  AuthenticationException();
        }
        ByteSource slat=ByteSource.Util.bytes(user.getSalt());
        SimpleAuthenticationInfo info=new SimpleAuthenticationInfo(
                user,
                user.getPassword(),
                slat,
                this.getName());
        return info;
    }

    /**
     * 授权
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        /**
         * 1、获取登录用户id
         * 2、基于用户id获取对应的角色并判断
         * 3、基于角色id获取对应的菜单并判断
         * 4、基于菜单id获取对应的权限标识并判断
         * 5、封装结果并返回
         */
        User user = (User)principalCollection.getPrimaryPrincipal();
        //List<Integer> role=
        return null;
    }
}
