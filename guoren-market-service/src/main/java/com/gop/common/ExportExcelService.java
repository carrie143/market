package com.gop.common;

import java.util.Map;

/**
 * Created by wuyanjie on 2018/4/28.
 */
public interface ExportExcelService {
    String createTemplateXlsByFileName(String templateName, Map<String, Object> model, String fileName);
}
