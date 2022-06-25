package com.example.mycommunity.pojo.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Email;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
public class UserUpdateDTO {
    @Range(min=0,max = 2,message = "gender值错误")
    private int gender;
    @Pattern(regexp = "^1(3\\d|4[5-9]|5[0-35-9]|6[567]|7[0-8]|8\\d|9[0-35-9])\\d{8}$",message = "手机号码格式错误")
    private String mobile;
    @Email
    private String email;
    @Past(message = "生日必须是过去的时间")
    private Date birthday;
}
