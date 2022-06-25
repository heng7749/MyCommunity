package com.example.mycommunity.config.shiro.realms;

import com.example.mycommunity.config.shiro.MyByteSource;
import com.example.mycommunity.pojo.entity.Role;
import com.example.mycommunity.pojo.entity.User;
import com.example.mycommunity.service.imp.UserServiceImp;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class MyRealm extends AuthorizingRealm {
    @Autowired
    private UserServiceImp userServiceImp;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String account = (String) principalCollection.getPrimaryPrincipal();
        List<Role> roles = userServiceImp.listRolesByAccount(account);
        if(!CollectionUtils.isEmpty(roles)){
            SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
            roles.forEach(role -> {
                System.out.println(role.getRole());
                authorizationInfo.addRole(role.getRole());
            });
            return authorizationInfo;
        }
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String account = (String) authenticationToken.getPrincipal();
        User user = userServiceImp.getByAccount(account);
        if (user != null && account.equals(user.getAccount())) {
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user.getAccount(),user.getPassword(), new MyByteSource(user.getSalt()),this.getName());
            return info;
        }
        return null;
    }
}
