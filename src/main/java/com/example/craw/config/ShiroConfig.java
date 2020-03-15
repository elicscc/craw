package com.example.craw.config;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("manager")DefaultWebSecurityManager defaultWebSecurityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);
        Map<String,String> filerMap=new LinkedHashMap<>();
        /**
         * anon 无需认证登录可以访问
         * authc 必须登录认证可访问
         * perms  必须有资源权限才能访问
         * user 如果使用rememberMe的功能才能直接访问
         * role 该资源必须得到角色权限才能访问
         */

        filerMap.put("/login.html","anon");
        filerMap.put("/reg.html","anon");
        filerMap.put("/index.html","anon");
        filerMap.put("/**.js","anon");
        filerMap.put("/**.css","anon");

        filerMap.put("/av.html","authc");
        //授权过滤器
        //filerMap.put("/add","perms[user:add]");
       // filerMap.put("/update","perms[user:update]");
        filerMap.put("/","anon");
        shiroFilterFactoryBean.setLoginUrl("/login.html");
        //shiroFilterFactoryBean.setUnauthorizedUrl("/noAuth");//未授权的401页面替换
       shiroFilterFactoryBean.setFilterChainDefinitionMap(filerMap);
        return shiroFilterFactoryBean;
    }

    /**
     * 创建DefaultWebSecurityManager
     *
     * @param userRealm
     * @return
     */
    @Bean(name="manager")
    public DefaultWebSecurityManager defaultWebSecurityManager(@Qualifier("real") UserRealm userRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm);
        return securityManager;
    }

    /**
     * 创建Realm
     *
     * @return
     */
    @Bean(name="real")
    public UserRealm userRealm() {
        return new UserRealm();

    }

}
