package com.example.mycommunity.config;

import com.example.mycommunity.config.shiro.cache.RedisCacheManager;
import com.example.mycommunity.config.shiro.realms.MyRealm;
import com.example.mycommunity.utils.ShiroConstants;
import com.example.mycommunity.utils.UserConstant;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    //1.创建ShiroFilter
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //给filter设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);
        //配置系统受限资源
        //配置资源的公共资源
        Map<String, String> map = new HashMap<>();
        map.put("/test","anon");
        map.put("/**","authc");     //请求这个资源1需要认证和授权
        map.put("/user/login","anon");
        map.put("/user/register","anon");
        map.put("/userRegister.html","anon");

        //默认认证路径
        shiroFilterFactoryBean.setLoginUrl("/userLogin.html");

//        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);

        return shiroFilterFactoryBean;
    }

    //2.创建安全管理器
    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager(Realm realm){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        //给安全管理器设置realm
        defaultWebSecurityManager.setRealm(realm);
        return defaultWebSecurityManager;
    }

    //3.创建自定义realm
    @Bean("realm")
    public Realm getRealm(){
        MyRealm myRealm = new MyRealm();
        //修改凭证校验匹配器
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        //设置加密算法为MD5
        hashedCredentialsMatcher.setHashAlgorithmName("MD5");
        //设置迭代的次数
        hashedCredentialsMatcher.setHashIterations(UserConstant.PASSWORD_HASH_ITERATIONS);

//        //开启缓存管理
        myRealm.setCacheManager(new RedisCacheManager());
        myRealm.setCachingEnabled(true);    //开启全局缓存

        myRealm.setAuthenticationCachingEnabled(true);    //认证缓存
        myRealm.setAuthenticationCacheName(ShiroConstants.AUTHENTICATION_CACHE_NAME);//设置缓存的字段名

        myRealm.setAuthorizationCachingEnabled(true);     //授权缓存
        myRealm.setAuthorizationCacheName(ShiroConstants.AUTHORIZATION_CACHE_NAME);//设置缓存的字段名

        myRealm.setCredentialsMatcher(hashedCredentialsMatcher);
        return myRealm;
    }

//    // 解决shiro权限注解控制认证时404的错误
//    @Bean
//    public static DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
//        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
//        /**
//         * setUsePrefix(false)用于解决一个奇怪的bug。在引入spring aop的情况下。
//         * 在@Controller注解的类的方法中加入@RequiresRole等shiro注解，会导致该方法无法映射请求，导致返回404。
//         * 加入这项配置能解决这个bug
//         */
//        defaultAdvisorAutoProxyCreator.setUsePrefix(true);
//        return defaultAdvisorAutoProxyCreator;
//    }
@Bean("lifecycleBeanPostProcessor")
public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
    return new LifecycleBeanPostProcessor();
}

    @Bean
    @DependsOn({"lifecycleBeanPostProcessor"})
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    @Bean("authorizationAttributeSourceAdvisor")
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new
                AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
}
