package com.seclib.htbp.user.config

import org.mybatis.spring.annotation.MapperScan
import org.springframework.context.annotation.Configuration

@Configuration
@MapperScan("com.seclib.htbp.user.mapper")
open class UserConfig