package com.seclib.htbp.common.exception

import org.springframework.web.bind.annotation.ControllerAdvice
import java.lang.Exception
import org.springframework.web.bind.annotation.ResponseBody
import com.seclib.htbp.common.exception.HtbpException
import com.seclib.htbp.common.result.ResultCodeEnum
import io.swagger.annotations.ApiModel
import java.lang.RuntimeException
import io.swagger.annotations.ApiModelProperty
import lombok.Data

/**
 * 自定义全局异常类
 *
 * @author qy
 */
@Data
@ApiModel(value = "自定义全局异常类")
class HtbpException : RuntimeException {
    @ApiModelProperty(value = "异常状态码")
    private var code: Int

    /**
     * 通过状态码和错误消息创建异常对象
     * @param message
     * @param code
     */
    constructor(message: String?, code: Int) : super(message) {
        this.code = code
    }

    /**
     * 接收枚举类型对象
     * @param resultCodeEnum
     */
    constructor(resultCodeEnum: ResultCodeEnum) : super(resultCodeEnum.message) {
        this.code = resultCodeEnum.code
    }

    override fun toString(): String {
        return "HtbpException{" +
                "code=" + code +
                ", message=" + message +
                '}'
    }
}