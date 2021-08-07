package com.seclib.easyexcel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.Map;

public class ExcelListener extends AnalysisEventListener<UserDate> {

    //read excel file line by line, start from 2nd line.
    @Override
    public void invoke(UserDate userDate, AnalysisContext analysisContext) {
        System.out.println(userDate);
    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println(headMap);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
