package com.seclib.htbp.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.seclib.htbp.model.user.UserInfo;
import com.seclib.htbp.vo.user.LoginVo;
import com.seclib.htbp.vo.user.UserAuthVo;
import com.seclib.htbp.vo.user.UserInfoQueryVo;

import java.util.Map;

public interface UserInfoService extends IService<UserInfo> {

    Map<String, Object> loginUser(LoginVo loginVo);

    /**
     * 根据微信openid获取用户信息
     * @param openid
     * @return
     */
    UserInfo getByOpenid(String openid);

    UserInfo selectWxInfoOpenId(String openId);

    void userAuth(Long userId, UserAuthVo userAuthVo);

    IPage<UserInfo> selectPage(Page<UserInfo> pageParam, UserInfoQueryVo userInfoQueryVo);

    void lock(Long userId, Integer status);
}
