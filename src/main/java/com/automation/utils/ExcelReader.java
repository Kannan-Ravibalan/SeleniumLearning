package com.automation.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

public class ExcelReader {
    private Workbook workbook;
    private Sheet sheet;

    public ExcelReader(String filePath, String sheetName) {
        try {
            FileInputStream file = new FileInputStream(filePath);
            workbook = new XSSFWorkbook(file);
            sheet = workbook.getSheet(sheetName);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load Excel file");
        }
    }

    public String getCellData(int rowIndex, int colIndex) {
        Row row = sheet.getRow(rowIndex);
        if (row != null) {
            Cell cell = row.getCell(colIndex);
            if (cell != null) {
                return cell.toString();
            }
        }
        return "";
    }

    public int getRowCount() {
        return sheet.getPhysicalNumberOfRows();
    }

    public int getColumnCount(int rowIndex) {
        Row row = sheet.getRow(rowIndex);
        return (row != null) ? row.getPhysicalNumberOfCells() : 0;
    }

    public int getColumnIndexByHeader(String headerName) {
        Row headerRow = sheet.getRow(0);
        if (headerRow != null) {
            for (Cell cell : headerRow) {
                if (cell.toString().equalsIgnoreCase(headerName)) {
                    return cell.getColumnIndex();
                }
            }
        }
        throw new RuntimeException("Header not found: " + headerName);
    }

    public String getDataByKeywordAndHeader(String keyword, String searchHeader, String returnHeader) {
        int searchColumn = getColumnIndexByHeader(searchHeader);
        int returnColumn = getColumnIndexByHeader(returnHeader);
        for (Row row : sheet) {
            Cell searchCell = row.getCell(searchColumn);
            if (searchCell != null && searchCell.toString().equalsIgnoreCase(keyword)) {
                Cell returnCell = row.getCell(returnColumn);
                return (returnCell != null) ? returnCell.toString() : "";
            }
        }
        return "";
    }

    public void writeDataByKeywordAndHeader(String keyword, String searchHeader, String writeHeader, String value, String filePath) {
        try {
            int searchColumn = getColumnIndexByHeader(searchHeader);
            int writeColumn = getColumnIndexByHeader(writeHeader);
            for (Row row : sheet) {
                Cell searchCell = row.getCell(searchColumn);
                if (searchCell != null && searchCell.toString().equalsIgnoreCase(keyword)) {
                    Cell writeCell = row.getCell(writeColumn);
                    if (writeCell == null) {
                        writeCell = row.createCell(writeColumn);
                    }
                    writeCell.setCellValue(value);
                    break;
                }
            }
            FileOutputStream fileOut = new FileOutputStream(filePath);
            workbook.write(fileOut);
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to write to Excel file");
        }
    }
}