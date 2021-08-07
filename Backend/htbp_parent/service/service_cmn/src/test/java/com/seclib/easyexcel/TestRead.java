package com.seclib.easyexcel;

import com.alibaba.excel.EasyExcel;

public class TestRead {
    public static void main(String[] args){
        String fileName = "C:\\Disk_D\\Projects\\HospitalTimeBooingPlatform\\Test\\01.xlsx";
        EasyExcel.read(fileName,UserDate.class, new ExcelListener()).sheet()
                .doRead();
    }
}
