package com.seclib.htbp.hosp.controller

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.seclib.htbp.common.utils.MD5
import com.seclib.htbp.hosp.service.HospitalSetService
import com.seclib.htbp.model.hosp.HospitalSet
import com.seclib.htbp.vo.hosp.HospitalSetQueryVo
import com.seclib.htbp.common.result.Result
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.util.StringUtils
import org.springframework.web.bind.annotation.*
import java.util.*


@Api(tags = ["Hospital information configuration."])
@RestController
@RequestMapping("/admin/hosp/hospitalSet") //@CrossOrigin
class HospitalSetController {
    @Autowired
    private val hospitalSetService: HospitalSetService? = null
    @ApiOperation(value = "Get the configuration of hospital.")
    @GetMapping("findAll")
    fun findAllHospitalSet(): Result<*> {
        val list = hospitalSetService!!.list()
        return Result.ok(list)
    }

    @DeleteMapping("{id}")
    fun removeHospSet(@PathVariable id: Long?): Result<*> {
        val flag = hospitalSetService!!.removeById(id)
        return if (flag) {
            Result.ok<Any>()
        } else {
            Result.fail<Any>()
        }
    }

    @PostMapping("findPageHospSet/{current}/{limit}")
    fun findPageHospSet(
        @PathVariable current: Long,
        @PathVariable limit: Long,
        @RequestBody(required = false) hospitalSetQueryVo: HospitalSetQueryVo
    ): Result<*> {
        val page = Page<HospitalSet>(current, limit)
        val wrapper = QueryWrapper<HospitalSet>()
        val hoscode = hospitalSetQueryVo.hoscode
        val hosname = hospitalSetQueryVo.hosname
        if (!StringUtils.isEmpty(hosname)) {
            wrapper.like("hosname", hosname)
        }
        if (!StringUtils.isEmpty(hoscode)) {
            wrapper.eq("hoscode", hoscode)
        }
        val pageHospitalSet = hospitalSetService!!.page(page, wrapper)
        return Result.ok(pageHospitalSet)
    }

    //4 添加医院设置
    @PostMapping("saveHospitalSet")
    fun saveHospitalSet(@RequestBody hospitalSet: HospitalSet): Result<*> {
        //设置状态 1 使用 0 不能使用
        hospitalSet.status = 1
        //签名秘钥
        val random = Random()
        hospitalSet.signKey = MD5.encrypt(
            System.currentTimeMillis().toString() + "" + random.nextInt(1000)
        )
        //调用service
        val save = hospitalSetService!!.save(hospitalSet)
        return if (save) {
            Result.ok<Any>()
        } else {
            Result.fail<Any>()
        }
    }

    //5 根据id获取医院设置
    @GetMapping("getHospSet/{id}")
    fun getHospSet(@PathVariable id: Long?): Result<*> {
//        try {
//            //模拟异常
//            int a = 1/0;
//        }catch (Exception e) {
//            throw new YyghException("失败",201);
//        }
        val hospitalSet = hospitalSetService!!.getById(id)
        return Result.ok(hospitalSet)
    }

    //6 修改医院设置
    @PostMapping("updateHospitalSet")
    fun updateHospitalSet(@RequestBody hospitalSet: HospitalSet): Result<*> {
        val flag = hospitalSetService!!.updateById(hospitalSet)
        return if (flag) {
            Result.ok<Any>()
        } else {
            Result.fail<Any>()
        }
    }

    //7 批量删除医院设置
    @DeleteMapping("batchRemove")
    fun batchRemoveHospitalSet(@RequestBody idList: List<Long?>?): Result<*> {
        hospitalSetService!!.removeByIds(idList)
        return Result.ok<Any>()
    }

    //8 医院设置锁定和解锁
    @PutMapping("lockHospitalSet/{id}/{status}")
    fun lockHospitalSet(
        @PathVariable id: Long?,
        @PathVariable status: Int?
    ): Result<*> {
        //根据id查询医院设置信息
        val hospitalSet = hospitalSetService!!.getById(id)
        //设置状态
        hospitalSet!!.status = status
        //调用方法
        hospitalSetService.updateById(hospitalSet)
        return Result.ok<Any>()
    }

    //9 发送签名秘钥
    @PutMapping("sendKey/{id}")
    fun lockHospitalSet(@PathVariable id: Long?): Result<*> {
        val hospitalSet = hospitalSetService!!.getById(id)
        val signKey = hospitalSet!!.signKey
        val hoscode = hospitalSet.hoscode
        //TODO 发送短信
        return Result.ok<Any>()
    }
}