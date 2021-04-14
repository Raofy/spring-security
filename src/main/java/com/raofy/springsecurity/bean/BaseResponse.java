package com.raofy.springsecurity.bean;

import lombok.Data;

import javax.servlet.http.HttpServletResponse;

/**
 * @author raofy
 * @date 2021-04-14 11:26
 * @desc 请求响应返回的Json结构体
 */
@Data
public class BaseResponse<T> {

    private int status;

    private String message;

    private T data;

    public static BaseResponse failure(int value, String s) {
        BaseResponse<Object> response = new BaseResponse<>();
        response.setStatus(value);
        response.setMessage(s);
        return response;
    }

    public static BaseResponse success(String msg, Object data) {
        BaseResponse<Object> response = new BaseResponse<>();
        response.setStatus(HttpServletResponse.SC_OK);
        response.setMessage(msg);
        response.setData(data);
        return response;
    }
}
