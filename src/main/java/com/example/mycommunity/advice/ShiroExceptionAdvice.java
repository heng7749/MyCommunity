package com.example.mycommunity.advice;

import com.example.mycommunity.response.CommonResponse;
import com.example.mycommunity.response.ResponseCode;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ShiroExceptionAdvice {
    @ExceptionHandler(AuthorizationException.class)
    public CommonResponse authorizationExceptionHandler(AuthorizationException e) {
        return new CommonResponse(ResponseCode.ERROR.getCode(),"请先登录");
    }
}
