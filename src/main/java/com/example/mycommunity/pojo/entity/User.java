package com.example.mycommunity.pojo.entity;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {
    private int id;
    private String account;
    private String password;
    private String userName;
//    0未确定，1男，2女
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
