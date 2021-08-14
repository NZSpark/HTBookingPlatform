package com.seclib.htbp.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seclib.htbp.model.user.UserInfo;
import com.seclib.htbp.user.mapper.UserInfoMapper;
import com.seclib.htbp.user.service.UserInfoService;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

}

