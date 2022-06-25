package com.example.mycommunity.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserVO {
    private int id;
    private String account;
    private String password;
    private String userName;
    private int gender;
    private int experience;
    private int status;
    private String mobile;
    private String email;
    private Date birthday;
    private String avatar;
    private String salt;
    private Date createTime;
    private Date modifyTime;
}
