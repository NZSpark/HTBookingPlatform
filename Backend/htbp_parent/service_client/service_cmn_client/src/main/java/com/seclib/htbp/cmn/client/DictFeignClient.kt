package com.seclib.htbp.cmn.client

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.stereotype.Repository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient("service-cmn")
@Repository
interface DictFeignClient {
    @GetMapping("/admin/cmn/dict/getName/{dictCode}/{value}")
    fun getName(@PathVariable("dictCode") dictCode: String?, @PathVariable("value") value: String?): String?

    @GetMapping("/admin/cmn/dict/getName/{value}")
    fun getName(@PathVariable("value") value: String?): String?
}