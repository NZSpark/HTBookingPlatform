package com.seclib.htbp.cmn.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.seclib.htbp.model.cmn.Dict;
import com.seclib.htbp.model.hosp.HospitalSet;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface DictService extends IService<Dict> {
    List<Dict> findChildData(Long id);

    void exportDictData(HttpServletResponse response);

    void importDictData(MultipartFile file);

    String getDictName(String s, String value);

    List<Dict> findByDictCode(String dictCode);
}
