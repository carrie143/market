package com.gop.common.impl;

import com.gop.common.ExportExcelService;
import com.gop.exception.AppException;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.*;
import java.util.Map;

/**
 * Created by wuyanjie on 2018/4/28.
 */
@Service
@Slf4j
public class ExportExcelServiceImpl implements ExportExcelService{

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;
    @Override
    public String createTemplateXlsByFileName(String templateName, Map<String, Object> model, String fileName) {
        String xlsPath = "" + fileName;
        log.info("导出路径：" + xlsPath);
        try {
            createTemplateXls(templateName, model, xlsPath);
        } catch (Exception e) {
            throw new AppException("创建excel失败",e.getMessage());
        }
        return xlsPath;
    }

    /**
     * 使用ftl模板生成xls文件
     *
     * @param templateName
     *            模板名
     * @param model
     *            传入HashMap<String,Object>
     * @param xlsPath
     *            fileName 输出文件名
     * @return boolean 成功失败
     */
    public void createTemplateXls(String templateName, Map<String, Object> model, String xlsPath) {
        log.info("执行createTemplateXls start");
        Writer out = null;
        try {
            File file = new File(xlsPath);
            // 路径不存在则创建路径。
            if (!file.getParentFile().exists()) {
                log.info("路径不存在,创建",xlsPath);
                file.getParentFile().mkdirs();
            }

            Template t = freeMarkerConfigurer.getConfiguration().getTemplate(templateName);
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(xlsPath)), "UTF-8"));
            t.process(model, out);
            out.flush();
            out.close();
        } catch (Exception ex) {
            throw new AppException("生成execl失败",ex.getMessage());
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }
        }
        log.info("执行createTemplateXls end");
    }


}
