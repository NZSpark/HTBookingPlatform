package com.seclib.htbp.user.api

import com.seclib.htbp.common.result.Result.Companion.ok
import com.seclib.htbp.common.utils.AuthContextHolder.getUserId
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.beans.factory.annotation.Autowired
import com.seclib.htbp.user.service.UserInfoService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import com.seclib.htbp.vo.user.LoginVo
import com.seclib.htbp.vo.user.UserAuthVo
import com.seclib.htbp.common.result.Result

@RestController
@RequestMapping("/api/user")
class UserInfoApiController {
    @Autowired
    private val userInfoService: UserInfoService? = null
    @PostMapping("login")
    fun login(@RequestBody loginVo: LoginVo): Result<*> {
        val info = userInfoService!!.loginUser(loginVo)
        return ok(info)
    }

    //user authentication
    @PostMapping("auth/userAuth")
    fun userAuth(@RequestBody userAuthVo: UserAuthVo, request: HttpServletRequest?): Result<*> {
        getUserId(request!!)?.let { userInfoService!!.userAuth(it, userAuthVo) }
        return ok<Any>()
    }

    @GetMapping("auth/getUserInfo")
    fun getUserInfo(request: HttpServletRequest?): Result<*> {
        val userId = getUserId(request!!)
        val userInfo = userInfoService!!.getById(userId)
        return ok(userInfo)
    }
}