package com.seclib.easyexcel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

public class TestWrite {
    public static void main(String[] args){
        List<UserDate> list = new ArrayList<>();
        for(int i=0 ; i< 10; i++){
            UserDate data = new UserDate();
            data.setUid(i);
            data.setUserName("lucy"+i);
            list.add(data);
        }
        String fileName = "C:\\Disk_D\\Projects\\HospitalTimeBooingPlatform\\Test\\01.xlsx";
        EasyExcel.write(fileName,UserDate.class).sheet("User Information")
                .doWrite(list);
    }
}
