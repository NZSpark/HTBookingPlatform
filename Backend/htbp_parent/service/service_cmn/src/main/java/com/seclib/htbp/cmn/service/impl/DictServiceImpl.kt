package com.seclib.htbp.cmn.service.impl

import com.alibaba.excel.EasyExcel
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.seclib.htbp.cmn.mapper.DictMapper
import com.seclib.htbp.model.cmn.Dict
import com.seclib.htbp.cmn.service.DictService
import javax.servlet.http.HttpServletResponse
import com.seclib.htbp.vo.cmn.DictEeVo
import java.io.IOException
import org.springframework.cache.annotation.CacheEvict
import org.springframework.web.multipart.MultipartFile
import com.seclib.htbp.cmn.listener.DictListener
import org.springframework.beans.BeanUtils
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import java.net.URLEncoder
import java.util.ArrayList

@Service
open class DictServiceImpl : ServiceImpl<DictMapper?, Dict?>(), DictService {
    @Cacheable(value = ["dict"], keyGenerator = "keyGenerator")
    override fun findChildData(id: Long?): List<Dict?>? {
        val wrapper = QueryWrapper<Dict>()
        wrapper.eq("parent_id", id)
        val dictList = baseMapper!!.selectList(wrapper)
        for (dict in dictList) {
            if(dict != null) {
                val dictId = dict.id
                val isChild = isChild(dictId)
                dict.isHasChildren = isChild
            }
        }
        return dictList
    }

    override fun exportDictData(response: HttpServletResponse?) {
        try {
            //set header for file download.
            response?.contentType = "application/vnd.ms-excel"
            response?.characterEncoding = "utf-8"
            val fileName = URLEncoder.encode("DataDict", "UTF-8")
            response?.setHeader("Content-disposition", "attachment;filename=$fileName.xlsx")

            //database query
            val dictList = baseMapper!!.selectList(null)
            val dictVoList: MutableList<DictEeVo?> = ArrayList()
            for (dict in dictList) {
                val dictVo = DictEeVo()
                BeanUtils.copyProperties(dict, dictVo, DictEeVo::class.java)
                dictVoList.add(dictVo)
            }
            EasyExcel.write(response?.outputStream, DictEeVo::class.java).sheet("Data Dictionary").doWrite(dictVoList)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    @CacheEvict(value = ["dict"], allEntries = true)
    override fun importDictData(file: MultipartFile?) {
        try {
                EasyExcel.read(file?.inputStream, DictEeVo::class.java, DictListener(baseMapper!!)).sheet().doRead()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun getDictName(dictCode: String?, value: String?): String? {
        return if (StringUtils.isEmpty(dictCode)) {
            val queryWrapper = QueryWrapper<Dict>()
            queryWrapper.eq("value", value)
            val dict = baseMapper!!.selectOne(queryWrapper) ?: return ""
            dict.name
        } else {
            val codeDict = dictCode?.let { getDictByDictCode(it) }

            val parentId = codeDict?.id
            val finalDict = baseMapper!!.selectOne(
                QueryWrapper<Dict>().eq("parent_id", parentId)
                    .eq("value", value)
            )
            finalDict?.name
        }
    }

    override fun findByDictCode(dictCode: String?): List<Dict?>? {
        val dict = dictCode?.let { getDictByDictCode(it) }
        return findChildData(dict?.id)
    }

    private fun getDictByDictCode(dictCode: String): Dict? {
        val queryWrapper: QueryWrapper<Dict?> = QueryWrapper<Dict?>()
        queryWrapper.eq("dict_code", dictCode)
        return baseMapper!!.selectOne(queryWrapper)
    }

    private fun isChild(id: Long): Boolean {
        val wrapper = QueryWrapper<Dict>()
        wrapper.eq("parent_id", id)
        val count = baseMapper!!.selectCount(wrapper)
        return count > 0
    }
}