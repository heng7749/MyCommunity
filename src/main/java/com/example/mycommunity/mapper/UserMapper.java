package com.example.mycommunity.mapper;

import com.example.mycommunity.pojo.entity.Role;
import com.example.mycommunity.pojo.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("select id from user where account=#{account}")
    int getUserId(String account);

    @Result(property = "userName", column = "user_name")
    @Select("select id,account,user_name,avatar,password,salt from user where account = #{account}")
    User getByAccount(String account);
    // 用户注册
    @Insert("insert into user(account,user_name,password,salt,create_time) values(#{account},#{account},#{password},#{salt},#{createTime})")
    boolean userRegister(User user);

    @Select("select role.id,role.role from user left join user_role on user.id = user_role.user_id left join role on user_role.role_id = role.id where user.account = #{account}")
    List<Role> listRolesByAccount(String account);

    // 获取用户自己详细信息
    @Results(@Result(property = "userName", column = "user_name"))
    @Select("select id,account,user_name,nickname,create_time,gender,mobile,email,birthday,experience,avatar from user where id=#{userId}")
    User getDetailByUserId(Integer userId);
    // 更新用户详细信息
    @Update("update user set gender=#{gender},mobile=#{mobile},email=#{email},birthday=#{birthday},modify_time=#{modifyTime} where id=#{userId}")
    boolean updateUser(User user);
    // 更新头像
    @Update("update user set avatar=#{avatar} where id=#{userId}")
    boolean updateUserAvatar(Integer userId, String avatar);
    // 更新密码
    @Update("update user set password=#{password},salt=#{salt} where id=#{userId}")
    boolean updateUserPassword(Integer userId,String password,String salt);
}
