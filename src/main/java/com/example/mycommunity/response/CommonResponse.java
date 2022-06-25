package com.example.mycommunity.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;

@JsonSerialize(include =  JsonSerialize.Inclusion.NON_NULL)
//保证序列化json的时候,如果是null的对象,key也会消失
public class CommonResponse<T> implements Serializable{
    private int status;
    private String msg;
    private T data;

    public CommonResponse() {}
    public CommonResponse(int status){
        this.status = status;
    }
    public CommonResponse(int status, T data){
        this.status = status;
        this.data = data;
    }

    public CommonResponse(int status, String msg){
        this.status = status;
        this.msg = msg;
    }

    public CommonResponse(int status, String msg, T data){
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    @JsonIgnore
    //使之不在json序列化结果当中
    public boolean isSuccess(){
        return this.status == ResponseCode.SUCCESS.getCode();
    }

    public static <T> CommonResponse<T> createBySuccess(){
        return new CommonResponse<T>(ResponseCode.SUCCESS.getCode());
    }

    public static <T> CommonResponse<T> createBySuccessMessage(String msg){
        return new CommonResponse<T>(ResponseCode.SUCCESS.getCode(),msg);
    }

    public static <T> CommonResponse<T> createBySuccess(T data){
        return new CommonResponse<T>(ResponseCode.SUCCESS.getCode(),data);
    }

    public static <T> CommonResponse<T> createBySuccess(String msg,T data){
        return new CommonResponse<T>(ResponseCode.SUCCESS.getCode(),msg,data);
    }


    public static <T> CommonResponse<T> createByError(){
        return new CommonResponse<T>(ResponseCode.ERROR.getCode(),ResponseCode.ERROR.getDesc());
    }


    public static <T> CommonResponse<T> createByErrorMessage(String errorMessage){
        return new CommonResponse<T>(ResponseCode.ERROR.getCode(),errorMessage);
    }

    public static <T> CommonResponse<T> createByErrorCodeMessage(int errorCode,String errorMessage){
        return new CommonResponse<T>(errorCode,errorMessage);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "CommonResponse{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
