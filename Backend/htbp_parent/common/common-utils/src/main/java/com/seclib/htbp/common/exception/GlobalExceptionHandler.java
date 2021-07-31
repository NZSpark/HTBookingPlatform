package com.seclib.htbp.common.exception;

import com.seclib.htbp.common.result.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e){
        e.printStackTrace();
        return Result.fail();
    }

    @ExceptionHandler(HtbpException.class)
    @ResponseBody
    public Result error(HtbpException e){
        e.printStackTrace();
        return Result.fail();
    }

}
