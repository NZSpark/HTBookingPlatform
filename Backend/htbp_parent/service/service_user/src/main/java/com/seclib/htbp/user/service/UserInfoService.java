package com.seclib.htbp.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.seclib.htbp.model.user.UserInfo;
import com.seclib.htbp.vo.user.LoginVo;

import java.util.Map;

public interface UserInfoService extends IService<UserInfo> {
    Map<String, Object> login(LoginVo loginVo);
}
