package com.example.trucks_order.controller;

import com.example.trucks_order.error.BusinessException;
import com.example.trucks_order.error.EmBusinessError;
import com.example.trucks_order.response.CommonReturnType;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lmwis on 2019-01-31 13:59
 */
public class BaseController {
    //定义ExceptionHandler解决未被controller层处理的异常
    @ExceptionHandler(Exception.class)  //指定ExceptionHandler需要拦截的异常类，这里拦截所有异常
    @ResponseStatus(HttpStatus.OK)  //指定返回给页面的状态码，此处统一返回200
    @ResponseBody
    public Object handlerException(HttpServletRequest request, Exception exp){
        Map<String,Object> responseData = new HashMap<String,Object>();
        if(exp instanceof BusinessException){
            BusinessException businessException = (BusinessException) exp;
            responseData.put("errCode",businessException.getErrCode());
            responseData.put("errMsg",businessException.getErrMsg());
        }else{
            responseData.put("errCode", EmBusinessError.UNKNOWN_ERROR.getErrCode());
//            responseData.put("errMsg",EmBusinessError.UNKNOWN_ERROR.getErrMsg());
            responseData.put("errMsg",exp.getMessage());
        }
        return CommonReturnType.create(responseData,"fail");
    }


}
