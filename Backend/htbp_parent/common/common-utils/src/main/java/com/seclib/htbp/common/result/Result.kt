package com.seclib.htbp.common.result

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import lombok.Data

/**
 * 全局统一返回结果类
 */
@Data
@ApiModel(value = "全局统一返回结果")
open class Result<T> {
    @ApiModelProperty(value = "返回码")
    var code: Int? = null

    @ApiModelProperty(value = "返回消息")
    var message: String? = null

    @ApiModelProperty(value = "返回数据")
    var data: T? = null

    fun message(msg: String): Result<T> {
        this.message = msg
        return this
    }

    fun code(code: Int): Result<T> {
        this.code = code
        return this
    }

    val isOk: Boolean
        get() = this.code!!.toInt() == ResultCodeEnum.SUCCESS.code.toInt()

    companion object {
        protected fun <T> build(data: T?): Result<T> {
            val result = Result<T>()
            if (data != null) result.data = data
            return result
        }

        fun <T> build(body: T?, resultCodeEnum: ResultCodeEnum): Result<T> {
            val result = build(body)
            result.code = resultCodeEnum.code
            result.message = resultCodeEnum.message
            return result
        }

        fun <T> build(code: Int?, message: String?): Result<T?> {
            val result = build<T?>(null)
            result.code = code
            result.message = message
            return result
        }

        fun <T> ok(): Result<T?> {
            return ok(null)
        }

        /**
         * 操作成功
         * @param data
         * @param <T>
         * @return
        </T> */
        @JvmStatic
        fun <T> ok(data: T): Result<T> {
            val result = build(data)
            return build(data, ResultCodeEnum.SUCCESS)
        }

        fun <T> fail(): Result<T> {
            return fail(null)
        }

        /**
         * 操作失败
         * @param data
         * @param <T>
         * @return
        </T> */
        fun <T> fail(data: T?): Result<T> {
            val result = build(data)
            return build(data, ResultCodeEnum.FAIL)
        }
    }
}