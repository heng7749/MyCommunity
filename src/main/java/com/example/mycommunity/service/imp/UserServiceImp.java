package com.example.mycommunity.service.imp;

import com.example.mycommunity.mapper.UserMapper;
import com.example.mycommunity.pojo.dto.UserUpdateDTO;
import com.example.mycommunity.pojo.entity.Role;
import com.example.mycommunity.pojo.entity.User;
import com.example.mycommunity.service.UserService;
import com.example.mycommunity.utils.SaltUtils;
import com.example.mycommunity.utils.UserConstant;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public int getUserId(String account) {
        return userMapper.getUserId(account);
    }

    @Override
    public boolean userRegister(User user) {
        String salt = SaltUtils.getSalt(UserConstant.SALT_LENGTH);
        Md5Hash md5Hash = new Md5Hash(user.getPassword(), salt, UserConstant.PASSWORD_HASH_ITERATIONS);
        user.setSalt(salt);
        user.setPassword(md5Hash.toHex());
        user.setCreateTime(new Date());
        return userMapper.userRegister(user);
    }

    @Override
    public User getByAccount(String account) {
        return userMapper.getByAccount(account);
    }

    @Override
    public List<Role> listRolesByAccount(String account) {
        return userMapper.listRolesByAccount(account);
    }

    @Override
        public boolean updateUserDetail(UserUpdateDTO dto) {
        User user = User.builder()
                .id((int) SecurityUtils.getSubject().getSession().getId())
                .birthday(dto.getBirthday())
                .gender(dto.getGender())
                .email(dto.getEmail())
                .mobile(dto.getMobile())
                .modifyTime(new Date()).build();

        return userMapper.updateUser(user);
    }

    @Override
    public boolean updateUserPassword(int userId, String newPassword) {
        String salt = SaltUtils.getSalt(UserConstant.SALT_LENGTH);
        Md5Hash md5Hash = new Md5Hash(newPassword, salt, UserConstant.PASSWORD_HASH_ITERATIONS);
        return userMapper.updateUserPassword(userId,md5Hash.toHex(),salt);
    }
}
