package com.seclib.htbp.user.service.impl

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.seclib.htbp.common.helper.JwtHelper.createToken
import com.seclib.htbp.enums.AuthStatusEnum.Companion.getStatusNameByStatus
import org.springframework.beans.factory.annotation.Autowired
import com.seclib.htbp.user.service.UserInfoService
import com.seclib.htbp.common.exception.HtbpException
import java.util.HashMap
import com.seclib.htbp.user.service.PatientService
import com.seclib.htbp.vo.user.LoginVo
import com.seclib.htbp.vo.user.UserAuthVo
import com.seclib.htbp.common.result.ResultCodeEnum
import com.seclib.htbp.user.mapper.UserInfoMapper
import org.springframework.data.redis.core.RedisTemplate
import com.seclib.htbp.enums.AuthStatusEnum
import com.seclib.htbp.model.user.UserInfo
import com.seclib.htbp.vo.user.UserInfoQueryVo
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils

@Service
open class UserInfoServiceImpl : ServiceImpl<UserInfoMapper?, UserInfo?>(), UserInfoService {
    @Autowired
    private val redisTemplate: RedisTemplate<String, String>? = null

    @Autowired
    private val patientService: PatientService? = null
    override fun loginUser(loginVo: LoginVo): Map<String, Any> {
        val phone: String? = loginVo.phone
        val code: String? = loginVo.code
        //校验参数
        if (StringUtils.isEmpty(phone) ||
            StringUtils.isEmpty(code)
        ) {
            throw HtbpException(ResultCodeEnum.PARAM_ERROR)
        }
        val redisCode = redisTemplate!!.opsForValue()[phone]
        if (code != redisCode) {
            throw HtbpException(ResultCodeEnum.CODE_ERROR)
        }
        var userInfo: UserInfo? = null
        if (!StringUtils.isEmpty(loginVo.openid)) {
            userInfo = getByOpenid(loginVo.openid)
            if (null != userInfo) {
                userInfo.phone = loginVo.phone
                updateById(userInfo)
            } else {
                throw HtbpException(ResultCodeEnum.DATA_ERROR)
            }
        }
        if (userInfo == null) {
            //手机号已被使用
            val queryWrapper = QueryWrapper<UserInfo>()
            queryWrapper.eq("phone", phone)
            //获取会员
            userInfo = baseMapper!!.selectOne(queryWrapper)
            if (null == userInfo) {
                userInfo = UserInfo()
                userInfo.name = ""
                userInfo.phone = phone
                userInfo.status = 1
                save(userInfo)
            }
        }

        //校验是否被禁用
        if (userInfo.status === 0) {
            throw HtbpException(ResultCodeEnum.LOGIN_DISABLED_ERROR)
        }

        //TODO 记录登录

        //返回页面显示名称
        val map= mutableMapOf<String,Any>()
        var name: String? = userInfo.name
        if (StringUtils.isEmpty(name)) {
            name = userInfo.nickName
        }
        if (StringUtils.isEmpty(name)) {
            name = userInfo.phone
        }
        map["name"] = name!!
        val token = createToken(userInfo.id, name)
        map["token"] = token
        return map
    }

    override fun getByOpenid(openid: String?): UserInfo? {
        return baseMapper!!.selectOne(QueryWrapper<UserInfo>().eq("openid", openid))
    }

    override fun selectWxInfoOpenId(openId: String): UserInfo? {
        val queryWrapper =
            QueryWrapper<UserInfo>()
        queryWrapper.eq("openid", openId)
        return baseMapper!!.selectOne(queryWrapper)
    }

    override fun userAuth(userId: Long, userAuthVo: UserAuthVo) {
        //query user information by Id
        val userInfo = baseMapper!!.selectById(userId) ?: return
        userInfo.name = userAuthVo.name
        //其他认证信息
        userInfo.certificatesType = userAuthVo.certificatesType
        userInfo.certificatesNo = userAuthVo.certificatesNo
        userInfo.certificatesUrl = userAuthVo.certificatesUrl
        userInfo.authStatus = AuthStatusEnum.AUTH_RUN.status
        //进行信息更新
        baseMapper!!.updateById(userInfo)
    }

    override fun selectPage(pageParam: Page<UserInfo>, userInfoQueryVo: UserInfoQueryVo): IPage<UserInfo> {
        val name: String? = userInfoQueryVo.keyword
        val status: Int? = userInfoQueryVo.status
        val authStatus: Int? = userInfoQueryVo.authStatus
        val createTimeBegin: String? = userInfoQueryVo.createTimeBegin
        val createTimeEnd: String? = userInfoQueryVo.createTimeEnd
        val queryWrapper = QueryWrapper<UserInfo>()
        if (!StringUtils.isEmpty(name)) {
            queryWrapper.like("name", name)
        }
        if (!StringUtils.isEmpty(status)) {
            queryWrapper.eq("status", status)
        }
        if (!StringUtils.isEmpty(authStatus)) {
            queryWrapper.eq("auth_status", authStatus)
        }
        if (!StringUtils.isEmpty(createTimeBegin)) {
            queryWrapper.ge("createTimeBegin", createTimeBegin)
        }
        if (!StringUtils.isEmpty(createTimeEnd)) {
            queryWrapper.lt("createTimeEnd", createTimeEnd)
        }
        val userInfoPage: IPage<UserInfo> = baseMapper!!.selectPage<Page<UserInfo>>(pageParam, queryWrapper)
        userInfoPage.records.stream().forEach { item: UserInfo -> packUserInfo(item) }
        return userInfoPage
    }

    override fun lock(userId: Long, status: Int) {
        if (status == 0 || status == 1) {
            val userInfo = baseMapper!!.selectById(userId) ?: return
            userInfo.status = status
            baseMapper!!.updateById(userInfo)
        }
    }

    override fun show(userId: Long): Map<String, Any?> {
        val map: MutableMap<String, Any?> = HashMap()
        val userInfo = packUserInfo(baseMapper!!.selectById(userId)!!)
        map["userInfo"] = userInfo
        val patientList = patientService!!.findAllByUserId(userId)
        map["patientList"] = patientList
        return map
    }

    override fun approval(userId: Long, authStatus: Int) {
        if (authStatus == 2 || authStatus == -1) {
            val userInfo = baseMapper!!.selectById(userId) ?: return
            userInfo.authStatus = authStatus
            baseMapper!!.updateById(userInfo)
        }
    }

    private fun packUserInfo(userInfo: UserInfo): UserInfo {
        userInfo.param["authStatusString"] = userInfo.authStatus?.let { getStatusNameByStatus(it) }!!
        val statusString = if (userInfo.status === 0) "lock" else "normal"
        userInfo.param["statusString"] = statusString
        return userInfo
    }
}