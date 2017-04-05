package com.hand.hap.excel.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hand.hap.excel.ExcelException;

/**
 * Created by jialong.zuo@hand-china.com on 2017/1/22.
 */
public interface IHapExcelImportService {
    void loadExcel(InputStream inputStream, String tableName)
            throws IOException, SQLException, ExcelException, ParseException;

}
