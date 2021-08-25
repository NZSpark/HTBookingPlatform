package com.seclib.htbp.user.service

import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.baomidou.mybatisplus.extension.service.IService
import com.seclib.htbp.vo.user.LoginVo
import com.seclib.htbp.vo.user.UserAuthVo
import com.seclib.htbp.model.user.UserInfo
import com.seclib.htbp.vo.user.UserInfoQueryVo

interface UserInfoService : IService<UserInfo?> {
    fun loginUser(loginVo: LoginVo): Map<String, Any>

    /**
     * 根据微信openid获取用户信息
     * @param openid
     * @return
     */
    fun getByOpenid(openid: String?): UserInfo?
    fun selectWxInfoOpenId(openId: String): UserInfo?
    fun userAuth(userId: Long, userAuthVo: UserAuthVo)
    fun selectPage(pageParam: Page<UserInfo>, userInfoQueryVo: UserInfoQueryVo): IPage<UserInfo>
    fun lock(userId: Long, status: Int)
    fun show(userId: Long): Map<String, Any?>
    fun approval(userId: Long, authStatus: Int)
}