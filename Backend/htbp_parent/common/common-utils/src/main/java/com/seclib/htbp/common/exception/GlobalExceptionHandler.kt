package com.seclib.htbp.common.exception

import org.springframework.web.bind.annotation.ControllerAdvice
import java.lang.Exception
import org.springframework.web.bind.annotation.ResponseBody
import com.seclib.htbp.common.exception.HtbpException
import com.seclib.htbp.common.result.Result
import io.swagger.annotations.ApiModel
import java.lang.RuntimeException
import io.swagger.annotations.ApiModelProperty
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
open class GlobalExceptionHandler {
    @ExceptionHandler(Exception::class)
    @ResponseBody
    open fun error(e: Exception): Result<*> {
        e.printStackTrace()
        return Result.fail<Any>()
    }

    @ExceptionHandler(HtbpException::class)
    @ResponseBody
    open fun error(e: HtbpException): Result<*> {
        e.printStackTrace()
        return Result.fail<Any>()
    }
}