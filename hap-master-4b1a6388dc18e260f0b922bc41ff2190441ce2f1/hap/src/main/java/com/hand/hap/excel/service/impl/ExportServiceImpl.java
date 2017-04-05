package com.hand.hap.excel.service.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.hand.hap.core.IRequest;
import com.hand.hap.excel.dto.ColumnInfo;
import com.hand.hap.excel.dto.ExportConfig;
import com.hand.hap.excel.service.IExportService;

/**
 * Created by jialong.zuo@hand-china.com on 2016/11/30.
 */
@Service
public class ExportServiceImpl implements IExportService {

    @Autowired
    @Qualifier("sqlSessionFactory")
    SqlSessionFactory sqlSessionFactory;

    private static final String ENC = "UTF-8";

    private static final int rowMaxNum = 100000;

    @Override
    public void exportExcel(String sqlId, ExportConfig gridInfo, IRequest iRequest, OutputStream outputStream)
            throws IOException {

        String sheetName = gridInfo.getFileName();

        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet(sheetName);
        XSSFRow firstRow = sheet.createRow(0);
        List<XSSFCellStyle> styles = new ArrayList<>();
        int i = 0;
        for (ColumnInfo columnInfo : (List<ColumnInfo>) gridInfo.getColumnsInfo()) {
            XSSFCell firstCell = firstRow.createCell(i);
            firstCell.setCellValue(columnInfo.getTitle());
            // 设置列宽度
            sheet.setColumnWidth(i, columnInfo.getWidth() * 80);
            // 设置列字体align
            XSSFCellStyle cellStyle = wb.createCellStyle();
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            firstCell.setCellStyle(cellStyle);

            XSSFCellStyle cellStyles = wb.createCellStyle();
            styles.add(cellStyles);
            i++;
        }

        // row计数器
        final AtomicInteger count = new AtomicInteger(1);
        // sheet页row索引
        final AtomicInteger rowIndex = new AtomicInteger(1);
        // XSSFCellStyle cellStyle = wb.createCellStyle();
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            sqlSession.select(sqlId, gridInfo.getParam(), (resultContext -> {
                Object object = resultContext.getResultObject();
                // sheet分页
                int sheetNum = count.get() / rowMaxNum;
                if (count.get() % rowMaxNum == 0) {
                    XSSFSheet s = wb.createSheet(sheetName + sheetNum);
                    // 设置宽度
                    int im = 0;
                    for (ColumnInfo columnInfo : (List<ColumnInfo>) gridInfo.getColumnsInfo()) {
                        s.setColumnWidth(im, columnInfo.getWidth() * 80);
                        im++;
                    }
                    rowIndex.set(0);
                }
                XSSFSheet sheets = wb.getSheetAt(sheetNum);
                count.getAndIncrement();
                XSSFRow row = sheets.createRow(rowIndex.getAndIncrement());
                int ii = 0;
                for (ColumnInfo columnInfo : (List<ColumnInfo>) gridInfo.getColumnsInfo()) {
                    Object fieldObject = null;
                    try {
                        fieldObject = PropertyUtils.getProperty(object, columnInfo.getName());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    String type = columnInfo.getType();
                    String align = columnInfo.getAlign();
                    XSSFCell cell = row.createCell(ii++);

                    if ("center".equals(align)) {
                        styles.get(ii - 1).setAlignment(HorizontalAlignment.CENTER);
                    } else if ("left".equals(align)) {
                        styles.get(ii - 1).setAlignment(HorizontalAlignment.LEFT);
                    } else if ("right".equals(align)) {
                        styles.get(ii - 1).setAlignment(HorizontalAlignment.RIGHT);
                    } else {
                        if ("string".equals(type)) {
                            styles.get(ii - 1).setAlignment(HorizontalAlignment.LEFT);
                        } else if ("number".equals(type) || "int".equals(type)) {
                            styles.get(ii - 1).setAlignment(HorizontalAlignment.RIGHT);
                        } else {
                            styles.get(ii - 1).setAlignment(HorizontalAlignment.CENTER);
                        }
                    }
                    cell.setCellStyle(styles.get(ii - 1));

                    // 设置类型
                    if ("number".equals(type) || "int".equals(type)) {
                        Long field = (Long) fieldObject;
                        if (field == null) {
                            cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                            cell.setCellValue((String) null);
                        } else {
                            cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
                            cell.setCellValue(field);
                        }
                    } else if ("string".equals(type)) {
                        String field = (String) fieldObject;
                        cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                        cell.setCellValue(field);
                    } else if ("date".equals(type)) {
                        Date field = (Date) fieldObject;
                        cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                        cell.setCellValue(field);
                    } else {
                        cell.setCellValue((String) fieldObject);
                    }
                }
            }));
        }
        wb.write(outputStream);
    }

    @Override
    public void exportAndDownloadExcel(String sqlId, ExportConfig exportConfig, HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse, IRequest iRequest) throws IOException {
        httpServletResponse.addHeader("Content-Disposition",
                "attachment;filename=\"" + URLEncoder.encode(exportConfig.getFileName() + ".xlsx", ENC) + "\"");
        httpServletResponse.setContentType("application/vnd.ms-excel" + ";charset=" + ENC);
        httpServletResponse.setHeader("Accept-Ranges", "bytes");
        try (OutputStream outputStream = httpServletResponse.getOutputStream()) {
            exportExcel(sqlId, exportConfig, iRequest, outputStream);
        }
    }

}
