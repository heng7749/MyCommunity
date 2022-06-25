package com.example.mycommunity.controller;

import com.example.mycommunity.pojo.dto.UserUpdateDTO;
import com.example.mycommunity.pojo.entity.User;
import com.example.mycommunity.pojo.vo.UserVO;
import com.example.mycommunity.response.CommonResponse;
import com.example.mycommunity.response.ResponseCode;
import com.example.mycommunity.service.imp.UserServiceImp;
import com.example.mycommunity.utils.ParamFormatUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserServiceImp userServiceImp;

    @PostMapping("login")
    public CommonResponse login(@RequestBody User user) {
        CommonResponse commonResponse = new CommonResponse();
        if (ParamFormatUtil.checkAccount(user.getAccount()) && ParamFormatUtil.checkPassword(user.getPassword())) {
            Subject subject = SecurityUtils.getSubject();
            try {
                // 用户认证
                subject.login(new UsernamePasswordToken(user.getAccount(),user.getPassword()));
                Session session = subject.getSession();
                user = userServiceImp.getByAccount(user.getAccount());
                session.setAttribute("userId",user.getId());
                session.setAttribute("userName",user.getUserName());
                session.setAttribute("avatar",user.getAvatar());

                commonResponse.setStatus(ResponseCode.SUCCESS.getCode());
                commonResponse.setMsg("登录成功");
            } catch (UnknownAccountException e) {
                commonResponse.setStatus(ResponseCode.ERROR.getCode());
                commonResponse.setMsg("用户名不存在");
            } catch (IncorrectCredentialsException e) {
                commonResponse.setStatus(ResponseCode.ERROR.getCode());
                commonResponse.setMsg("密码不正确");
            } catch (Exception e) {
                e.printStackTrace();
                commonResponse.setStatus(ResponseCode.ERROR.getCode());
                commonResponse.setMsg("未知错误");
            }
        } else {
            commonResponse.setStatus(ResponseCode.ERROR.getCode());
            commonResponse.setMsg("用户名或密码不正确");
        }
        return commonResponse;
    }

    @PostMapping("register")
    public CommonResponse userRegister(@RequestBody User user){
        CommonResponse commonResponse = new CommonResponse();
        if (ParamFormatUtil.checkAccount(user.getAccount()) && ParamFormatUtil.checkPassword(user.getPassword())) {
            if (userServiceImp.userRegister(user)) {
                commonResponse.setStatus(ResponseCode.SUCCESS.getCode());
                commonResponse.setMsg("注册成功");
            } else {
                commonResponse.setStatus(ResponseCode.ERROR.getCode());
                commonResponse.setMsg("该用户已名存在");
            }
        } else {
            commonResponse.setStatus(ResponseCode.ERROR.getCode());
            commonResponse.setMsg("用户名或密码不正确");
        }
        return commonResponse;
    }

    @PostMapping("updateDetail")
    @RequiresAuthentication
    public CommonResponse updateUserDetail(@Validated @RequestBody UserUpdateDTO dto) {
        CommonResponse commonResponse = new CommonResponse();
        if (userServiceImp.updateUserDetail(dto)) {
            commonResponse.setStatus(ResponseCode.SUCCESS.getCode());
            commonResponse.setMsg("更新成功");
        } else {
            commonResponse.setStatus(ResponseCode.ERROR.getCode());
            commonResponse.setMsg("更新失败");
        }
        return commonResponse;
    }

    @PostMapping("updatePassword")
    @RequiresAuthentication
    public CommonResponse updatePassword(@RequestParam("newPassword") String newPassword, @RequestParam("oldPassword") String oldPassword) {
        CommonResponse commonResponse = new CommonResponse();
        if (ParamFormatUtil.checkPassword(newPassword) && ParamFormatUtil.checkPassword(oldPassword)) {
            Subject subject = SecurityUtils.getSubject();
            String account = (String)subject.getPrincipal();
            try {
                // 验证用户真实性
                subject.login(new UsernamePasswordToken(account,oldPassword));
                if (userServiceImp.updateUserPassword((int)subject.getSession().getAttribute("userId"),newPassword)) {
                    commonResponse.setStatus(ResponseCode.SUCCESS.getCode());
                    commonResponse.setMsg("更改成功");
                    // 更新成功后用新密码再登录一次
                    subject.logout();
                } else {
                    commonResponse.setStatus(ResponseCode.ERROR.getCode());
                    commonResponse.setMsg("更改失败");
                }

            } catch (IncorrectCredentialsException e) {
                commonResponse.setStatus(ResponseCode.ERROR.getCode());
                commonResponse.setMsg("密码不正确");
            } catch (Exception e) {
                e.printStackTrace();
                commonResponse.setStatus(ResponseCode.ERROR.getCode());
                commonResponse.setMsg("未知错误");
            }
        }
        return commonResponse;
    }
}
