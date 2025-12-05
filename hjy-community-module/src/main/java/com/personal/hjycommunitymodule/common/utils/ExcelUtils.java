package com.personal.hjycommunitymodule.common.utils;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.personal.hjycommunitymodule.common.core.exception.BaseException;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * @ClassName ExcelUtils
 * @Author liupanpan
 * @Date 2025/12/4
 * @Description 文件工具类
 */
public class ExcelUtils {

    private static final Logger log = LoggerFactory.getLogger(ExcelUtils.class);
    // 文件导出
    public static void exportExcel(HttpServletResponse response,Class<?> clazz, List<?> dataList, String fileName, ExportParams params) {
        Workbook workbook = ExcelExportUtil.exportExcel(params, clazz, dataList);
        // 下载
        downloadExcelFile(workbook,fileName,response);
    }

    private static void downloadExcelFile(Workbook workbook, String fileName, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        ServletOutputStream outputStream = null;
        try {
            response.setHeader("content-disposition","attachment; filename="+ URLEncoder.encode(fileName+".xls","UTF-8"));
            outputStream = response.getOutputStream();
            workbook.write(outputStream);

        } catch (Exception e) {
            log.error("导出Excel异常{}",e.getMessage());
            throw new BaseException(500,"导出Excel失败");
        } finally {
            try {
                outputStream.close();
                workbook.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
