package com.hand.hap.excel.service.impl;

import com.hand.hap.excel.ExcelException;
import com.hand.hap.excel.dto.ColumnInfo;
import com.hand.hap.excel.service.IHapExcelImportService;
import com.hand.hap.generator.service.impl.DBUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by jialong.zuo@hand-china.com on 2017/1/22.
 */
@Service
@Transactional
public class HapExcelImportService implements IHapExcelImportService {

    @Autowired
    @Qualifier(value = "dataSource")
    DataSource dataSource;

    private final int batchQueryCount = 100;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void loadExcel(InputStream inputStream, String tableName) throws IOException, ExcelException {
        Workbook wb=null;
        try {
             wb = WorkbookFactory.create(inputStream);
        } catch (InvalidFormatException e) {
            e.printStackTrace();
            throw new ExcelException(null, "excel流构建workbook出错", null);
        }
        // XSSFWorkbook wb = new XSSFWorkbook(inputStream);
        List<ColumnInfo> columnInfos = null;
        try {
            columnInfos = readColumnType(tableName);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ExcelException(null, "读取列信息失败", null);

        }
        Row rows; // 当前行索引
        int batchCount = 0;
        String sql = "insert into " + tableName + " (";

        Sheet sheet = wb.getSheetAt(0);
        Row row1 = sheet.getRow(1);
        if (!isContains(columnInfos, row1)) {
            throw new ExcelException(null, "excel数据验证失败：非法的列存在", null);
        }

        // 构造sql 插入的列
        Iterator<Cell> iterator = row1.cellIterator();
        while (iterator.hasNext()) {
            Cell cell = iterator.next();
            if (!(cell.equals(null) || cell.toString().equals("") || cell.toString().equals(" "))) {
                sql = sql + cell.toString() + ",";
            }
        }
        sql = sql.substring(0, sql.length() - 1) + ") values(";

        Iterator<Cell> iterator1 = row1.cellIterator();

        while (iterator1.hasNext()) {
            sql = sql + "?,";
            iterator1.next();
        }
        sql = sql.substring(0, sql.length() - 1) + ")";
        Connection conn = null;
        try (Connection connection = dataSource.getConnection()) {
            conn = connection;
            connection.setAutoCommit(false);
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                for (int i = 0; i < wb.getNumberOfSheets(); i++) {
                    sheet = wb.getSheetAt(i);

                    // 读取excel数据 读取行
                    int key;
                    if (i == 0) {
                        key = 2;
                    } else {
                        key = 0;
                    }
                    for (int j = key; j < sheet.getLastRowNum() + 1; j++) {
                        // 读取excel读取列
                        rows = sheet.getRow(j);
                        Iterator<Cell> iterator3 = row1.cellIterator();
                        int ii = 1;
                        while (iterator3.hasNext()) {
                            Cell cell = iterator3.next();
                            if (!(cell.equals(null) || cell.toString().equals("") || cell.toString().equals(" "))) {
                                String type = "STRING";
                                for (ColumnInfo columnInfo : columnInfos) {
                                    if (columnInfo.getName().toUpperCase().equals(cell.toString().toUpperCase())) {
                                        type = columnInfo.getType();
                                        break;
                                    }
                                }
                                Cell cells = rows.getCell(cell.getColumnIndex());
                                if (!(null == cells)) {
                                    switch (type) {
                                        case "DATE":
                                        case "TIMESTAMP":
                                        case "DATETIME":
                                        case "TIME":
                                            try {
                                                stmt.setDate(ii, new Date(cells.getDateCellValue().getTime()));
                                            } catch (IllegalStateException e) {
                                                e.printStackTrace();
                                                throw new ExcelException(null, cells.toString() + " 数据类型错误: " + (int)(j+1) + "行"
                                                        + (int)(cells.getColumnIndex()+1) + "列,期待的类型: " + type, null);
                                            }
                                            break;
                                        case "BIGINT":
                                        case "INT":
                                        case "DOUBLE":
                                        case "NUMBER":
                                        case "INTEGER":
                                        case "FLOAT":
                                        case "DECIMAL":
                                        case "TINYINT":
                                            try {
                                                stmt.setInt(ii, (int) cells.getNumericCellValue());
                                            } catch (IllegalStateException e) {
                                                e.printStackTrace();
                                                throw new ExcelException(null, cells.toString() + " 数据类型错误: " + (int)(j+1) + "行"
                                                        + (int)(cells.getColumnIndex()+1) + "列,期待的类型: " + type, null);
                                            }
                                            break;
                                        default:
                                            cells.setCellType(Cell.CELL_TYPE_STRING);
                                            stmt.setString(ii, cells.getStringCellValue());
                                            break;
                                    }

                                } else {
                                    switch (type.toUpperCase()) {
                                        case "VARCHAR":
                                        case "STRING":
                                            stmt.setNull(ii, Types.VARCHAR);
                                            break;
                                        case "CHAR":
                                            stmt.setNull(ii, Types.CHAR);
                                            break;
                                        case "INTEGER":
                                            stmt.setNull(ii, Types.INTEGER);
                                            break;
                                        case "BIGINT":
                                            stmt.setNull(ii, Types.BIGINT);
                                            break;
                                        case "DOUBLE":
                                            stmt.setNull(ii, Types.DOUBLE);
                                            break;
                                        case "DECIMAL":
                                            stmt.setNull(ii, Types.DECIMAL);
                                            break;
                                        case "DATE":
                                        case "DATETIME":
                                        case "TIME":
                                        case "TIMESTAMP":
                                            stmt.setNull(ii, Types.DATE);
                                            break;
                                    }

                                }
                                if(!(null==cells)) {
                                    logger.info("正在将数据装入batch... 第" + (int) (j + 1) + "行第" + (int) (cells.getColumnIndex() + 1) + "列....当前值：" + cells);
                                }
                            }
                            ii++;
                        }

                        // 写入数据库
                        stmt.addBatch();
                        batchCount++;
                        if (batchCount >= batchQueryCount) {
                            insertData(stmt);
                            batchCount = 0;
                        }
                    }
                    if (batchCount > 0) {
                        insertData(stmt);
                    }
                }
                conn.commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
                logger.info("插入数据失败，数据已回滚..");
            } catch (SQLException e1) {
                e1.printStackTrace();
                throw new ExcelException(null, "数据回滚失败: " + e.getMessage(), null);
            }
        }
        logger.info("数据导入成功");
    }

    private void insertData(PreparedStatement statement) throws ExcelException {
        try {
            statement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ExcelException(null, "数据插入失败: " + e.getMessage(), null);
        }
    }

    // 通过数据库构造columninfo信息类
    public List<ColumnInfo> readColumnType(String tableName) throws SQLException {
        List<ColumnInfo> columnInfos = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            ResultSet resultSet = DBUtil.getTableColumnInfo(tableName, connection.getMetaData());
            while (resultSet.next()) {
                ColumnInfo columnInfo = new ColumnInfo();
                String typeName = resultSet.getString("TYPE_NAME");
                String columnName = resultSet.getString("COLUMN_NAME");
                columnInfo.setName(columnName);
                columnInfo.setType(typeName);
                columnInfos.add(columnInfo);
            }
        }

        return columnInfos;
    }

    // 判断excel的列是否被数据表列包含。
    private boolean isContains(List<ColumnInfo> columnInfos, Row rowTalbeName) {
        boolean result = true;
        Iterator<Cell> iterator = rowTalbeName.cellIterator();

        while (iterator.hasNext()) {
            int key = 0;
            Cell cell = iterator.next();
            for (ColumnInfo columnInfo : columnInfos) {
                if (!(cell.equals(null) || cell.equals("") || cell.equals(" "))) {
                    if (columnInfo.getName().toUpperCase().equals(cell.toString().toUpperCase())) {
                        key = 1;
                    }
                }
            }
            if (key == 0) {
                result = false;
                break;
            }
        }
        return result;
    }

}
