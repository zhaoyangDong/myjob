package com.hand.hap.excel.controllers;

import java.io.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hand.hap.core.IRequest;
import com.hand.hap.excel.ExcelException;
import com.hand.hap.excel.dto.ColumnInfo;
import com.hand.hap.excel.dto.ExportConfig;
import com.hand.hap.excel.service.IExportService;
import com.hand.hap.excel.service.IHapExcelImportService;
import com.hand.hap.function.dto.Function;
import com.hand.hap.system.controllers.BaseController;

/**
 * Created by jialong.zuo@hand-china.com on 2016/11/30.
 */
@Controller
public class HapExcelController extends BaseController {
    @Autowired
    IExportService excelService;
    @Autowired
    IHapExcelImportService iImportService;

    @Autowired
    ObjectMapper objectMapper;

    @RequestMapping(value = "/sys/function/export")
    public void createXLS(HttpServletRequest request, @RequestParam String config,
            HttpServletResponse httpServletResponse) {
        IRequest requestContext = createRequestContext(request);
        try {
            JavaType type = objectMapper.getTypeFactory().constructParametrizedType(ExportConfig.class,
                    ExportConfig.class, Function.class, ColumnInfo.class);
            ExportConfig<Function, ColumnInfo> exportConfig = objectMapper.readValue(config, type);
            excelService.exportAndDownloadExcel("com.hand.hap.function.mapper.FunctionMapper.selectFunctions",
                    exportConfig, request, httpServletResponse, requestContext);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "sys/function/import")
    public void importXLS(HttpServletRequest request,HttpServletResponse response){

        // 在解析请求之前先判断请求类型是否为文件上传类型
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);


        // 文件上传处理工厂
        FileItemFactory factory = new DiskFileItemFactory();

        // 创建文件上传处理器
        ServletFileUpload upload = new ServletFileUpload(factory);

        List items = null;
        try {
            items = upload.parseRequest(request);
        } catch (FileUploadException e) {
            e.printStackTrace();
        }

        // 对所有请求信息进行判断
        Iterator iter = items.iterator();

        while (iter.hasNext()) {
            FileItem item = (FileItem) iter.next();
            // 信息为文件的格式
            if (!item.isFormField()) {

                try {
                    InputStream fs =  item.getInputStream();

                    iImportService.loadExcel(fs, "sys_code_b");
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }



            }
        }



    }

}
