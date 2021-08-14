package com.seclib.htbp.cmn.controller;

import com.seclib.htbp.cmn.service.DictService;
import com.seclib.htbp.common.result.Result;
import com.seclib.htbp.model.cmn.Dict;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Api(tags="Dictionary configuration.")
@RestController
@RequestMapping("/admin/cmn/dict")
//@CrossOrigin
public class DictController {
    @Autowired
    private DictService dictService;

    @PostMapping("importData")
    public Result importDict(MultipartFile file){
        dictService.importDictData(file);
        return Result.ok();
    }

    @GetMapping("exportData")
    public Result exportDict(HttpServletResponse response){
            dictService.exportDictData(response);
            return Result.ok();
    }

    @GetMapping("findChildData/{id}")
    public Result findChildData(@PathVariable Long id){
        List<Dict> list = dictService.findChildData(id);
        return Result.ok(list);

    }

    @GetMapping("getName/{dictCode}/{value}")
    public String getName(@PathVariable String dictCode, @PathVariable String value){
        String dictName =  dictService.getDictName(dictCode,value);
        return dictName;
    }

    @GetMapping("getName/{value}")
    public String getName(@PathVariable String value){
        String dictName =  dictService.getDictName("",value);
        return dictName;
    }

    @GetMapping("findByDictCode/{dictCode}")
    public Result   findByDictCode(@PathVariable String dictCode){
        List<Dict> dictList = dictService.findByDictCode(dictCode);
        return Result.ok(dictList);
    }

}
