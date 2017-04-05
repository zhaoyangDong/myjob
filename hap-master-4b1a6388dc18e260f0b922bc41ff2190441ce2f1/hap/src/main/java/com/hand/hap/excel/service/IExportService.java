package com.hand.hap.excel.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.excel.dto.ExportConfig;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by jialong.zuo@hand-china.com on 2016/11/30.
 */
public interface IExportService {

    void exportExcel(String sqlId, ExportConfig gridInfo, IRequest iRequest, OutputStream outputStream)
            throws IOException;

    void exportAndDownloadExcel(String sqlId, ExportConfig exportConfig, HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,IRequest iRequest) throws IOException;

}
