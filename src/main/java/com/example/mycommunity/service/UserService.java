package com.example.mycommunity.service;

import com.example.mycommunity.pojo.dto.UserUpdateDTO;
import com.example.mycommunity.pojo.entity.Role;
import com.example.mycommunity.pojo.entity.User;

import java.util.List;

public interface UserService {
    int getUserId(String userName);
    boolean userRegister(User user);
    User getByAccount(String account);
    List<Role> listRolesByAccount(String account);
    boolean updateUserDetail(UserUpdateDTO userUpdateDTO);
    boolean updateUserPassword(int userId,String newPassword);
}
