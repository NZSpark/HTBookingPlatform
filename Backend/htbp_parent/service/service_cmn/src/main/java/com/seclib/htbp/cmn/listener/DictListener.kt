package com.seclib.htbp.cmn.listener

import com.alibaba.excel.context.AnalysisContext
import com.alibaba.excel.event.AnalysisEventListener
import com.seclib.htbp.cmn.mapper.DictMapper
import com.seclib.htbp.vo.cmn.DictEeVo
import com.seclib.htbp.model.cmn.Dict
import org.springframework.beans.BeanUtils

class DictListener(private val dictMapper: DictMapper) : AnalysisEventListener<DictEeVo?>() {
    //read excel file line by line, start from 2nd line.
    override fun invoke(dictEeVo: DictEeVo?, analysisContext: AnalysisContext?) {
        val dict = Dict()
        BeanUtils.copyProperties(dictEeVo, dict)
        dictMapper.insert(dict)
    }

    override fun invokeHeadMap(headMap: Map<Int, String>, context: AnalysisContext) {
        println(headMap)
    }

    override fun doAfterAllAnalysed(analysisContext: AnalysisContext) {}
}