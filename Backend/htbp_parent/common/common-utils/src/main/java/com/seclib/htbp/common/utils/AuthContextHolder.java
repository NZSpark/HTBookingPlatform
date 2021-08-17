package com.seclib.htbp.common.utils;

import com.seclib.htbp.common.helper.JwtHelper;

import javax.servlet.http.HttpServletRequest;

public class AuthContextHolder {
    //current user id
    public static Long getUserId(HttpServletRequest request){
        //get token
        String token = request.getHeader("token");
        Long userId = JwtHelper.getUserId(token);
        return userId;
    }
    //current user name
    public static String getUserName(HttpServletRequest request){
        //get token
        String token = request.getHeader("token");
        String userName = JwtHelper.getUserName(token);
        return userName;
    }
}
