package com.seclib.htbp.user.controller

import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.beans.factory.annotation.Autowired
import com.seclib.htbp.user.service.UserInfoService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.PathVariable
import com.seclib.htbp.common.result.Result
import com.seclib.htbp.model.user.UserInfo
import com.seclib.htbp.vo.user.UserInfoQueryVo

@RestController
@RequestMapping("/admin/user")
class UserController {
    @Autowired
    private val userInfoService: UserInfoService? = null

    //用户列表（条件查询带分页）
    @GetMapping("{page}/{limit}")
    fun list(
        @PathVariable page: Long,
        @PathVariable limit: Long,
        userInfoQueryVo: UserInfoQueryVo
    ): Result<*> {
        val pageParam = Page<UserInfo>(
            page, limit
        )
        val pageModel = userInfoService!!.selectPage(pageParam, userInfoQueryVo)
        return Result.ok(pageModel)
    }

    //lock user
    @GetMapping("lock/{userId}/{status}")
    fun lock(@PathVariable userId: Long, @PathVariable status: Int): Result<*> {
        userInfoService!!.lock(userId, status)
        return Result.ok<Any>()
    }

    //user info
    @GetMapping("show/{userId}")
    fun show(@PathVariable userId: Long): Result<*> {
        val map = userInfoService!!.show(userId)
        return Result.ok(map)
    }

    //authorize
    @GetMapping("approval/{userId}/{authStatus}")
    fun approval(@PathVariable userId: Long, @PathVariable authStatus: Int): Result<*> {
        userInfoService!!.approval(userId, authStatus)
        return Result.ok<Any>()
    }
}