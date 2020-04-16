package com.koron.inwlms.util;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.util.CellRangeAddress;
 
public class ExcelUtil {
 
    /**
     * 导出Excel
     * @param sheetName sheet名称
     * @param title 标题
     * @param values 内容，可以修改为list  或者 map
     * @param valueRow 内容行数
     * @param wb HSSFWorkbook对象
     * @return
     */
    public static HSSFWorkbook getHSSFWorkbook(String sheetName,List<String[]> heads,List<String[]> headNums,List<String[]> values,int valueRow, HSSFWorkbook wb){
 
        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        if(wb == null){
            wb = new HSSFWorkbook();
        }
 
        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(sheetName);
 
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        HSSFRow row = sheet.createRow(0);
 
        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
 
        //声明列对象
        HSSFCell cell = null;
 
        //创建标题
//        for(int i=0;i<title.length;i++){
//            cell = row.createCell(i);
//            cell.setCellValue(title[i]);
//            cell.setCellStyle(style);
//        }
        
        String[] header;
        String[] headNum;
        int length = heads.size();
        for (int i = 0; i < length; i++) {
            header = heads.get(i);
            headNum = headNums.get(i);
            HSSFRow row1 = sheet.createRow(i);
            HSSFCell cellHeader;
            for (int j = 0; j < header.length; j++) {
                cellHeader = row1.createCell(j);
                cellHeader.setCellStyle(style);
                cellHeader.setCellValue(new HSSFRichTextString(header[j]));
            }
            // 动态合并单元格
            for (int j = 0; j < headNum.length; j++) {
                String[] temp = headNum[j].split(",");
                int startRow = Integer.parseInt(temp[0]);
                int overRow = Integer.parseInt(temp[1]);
                int startCol = Integer.parseInt(temp[2]);
                int overCol = Integer.parseInt(temp[3]);
                sheet.addMergedRegion(new CellRangeAddress(startRow, overRow, startCol, overCol));
            }
        }
        
        //创建内容
        for(int i=0;i<values.size();i++){
            row = sheet.createRow(i + valueRow);
            for(int j=0;j<values.get(i).length;j++){
                //将内容按顺序赋给对应的列对象
                row.createCell(j).setCellValue(values.get(i)[j]);
            }
        }
        if(values.size()>1) {
        	sheet.addMergedRegion(new CellRangeAddress(valueRow, valueRow+values.size()-1, 0, 0));
            sheet.addMergedRegion(new CellRangeAddress(valueRow, valueRow+values.size()-1, 1, 1));
            sheet.addMergedRegion(new CellRangeAddress(valueRow, valueRow+values.size()-1, 2, 2));
            sheet.addMergedRegion(new CellRangeAddress(valueRow, valueRow+values.size()-1, 3, 3));
            sheet.addMergedRegion(new CellRangeAddress(valueRow, valueRow+values.size()-1, 10, 10));
            sheet.addMergedRegion(new CellRangeAddress(valueRow, valueRow+values.size()-1, 11, 11));
            sheet.addMergedRegion(new CellRangeAddress(valueRow, valueRow+values.size()-1, 12, 12));
            sheet.addMergedRegion(new CellRangeAddress(valueRow, valueRow+values.size()-1, 13, 13));
            sheet.addMergedRegion(new CellRangeAddress(valueRow, valueRow+values.size()-1, 14, 14));
        }
        return wb;
    }
}