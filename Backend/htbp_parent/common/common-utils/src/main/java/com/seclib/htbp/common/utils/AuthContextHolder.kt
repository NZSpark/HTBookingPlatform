package com.seclib.htbp.common.utils

import com.seclib.htbp.common.helper.JwtHelper.getUserId
import com.seclib.htbp.common.helper.JwtHelper.getUserName
import javax.servlet.http.HttpServletRequest

object AuthContextHolder {
    //current user id
    @JvmStatic
    fun getUserId(request: HttpServletRequest): Long? {
        //get token
        val token = request.getHeader("token")
        return getUserId(token)
    }

    //current user name
    fun getUserName(request: HttpServletRequest): String? {
        //get token
        val token = request.getHeader("token")
        return getUserName(token)
    }
}