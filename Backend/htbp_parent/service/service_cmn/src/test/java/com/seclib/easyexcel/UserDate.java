package com.seclib.easyexcel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class UserDate {
    @ExcelProperty(value="UserNumber",index=0)
    private int uid;
    @ExcelProperty(value="UserName",index=1)
    private String userName;
}
