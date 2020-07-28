package com.koron.inwlms.util;



import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.usermodel.*;
import org.openxmlformats.schemas.drawingml.x2006.chart.*;
import org.swan.excel.Excel;
import org.swan.excel.Resource;

/**
 * @Author: ligui
 * @CreateTime: 2019-12-06 11:03
 * @Description: 报表导出excel工具类
 */
public class ExportReportUtil {

    public static final boolean MERGE_DATA_CELL = true;

    public static final boolean NOT_MERGE_DATA_CELL = false;

    public static final String PIE_CHART = "pie";

    public static final String HISTOGRAM_CHAR = "histogram";

    public static final String LINE_CHAR = "line";

    private static String templatePath = "static/excelTemplate/reportTemplate.xlsx";

    private static FormulaEvaluator evaluator;

    /**
     * 表格数据信息为javabean类型，导出不含图表的报表excel
     *
     * @param titleName       excel标题名
     * @param baseInfos       excel基础信息
     * @param titleInfo       表头信息
     * @param dataLists       表格数据信息
     * @param isMergeData     是否需要合并表格单元格 MERGE_DATA_CELL(需要合并)/NOT_MERGE_DATA_CELL(不需要)
     * @param mergeIndex      需要合并列的索引
     * @param benchmarkColumn 基准列索引
     * @param <T>
     * @return
     */
    public static <T> XSSFWorkbook export(String titleName, String[][] baseInfos, List<List<Map<String, Object>>> titleInfo, Collection<T> dataLists, boolean isMergeData, int[] mergeIndex, Integer benchmarkColumn) {
        try {
            XSSFWorkbook wb = null;
            InputStream file = Resource.getResourceInputStream(templatePath);
            if (file != null) {
                File tmp = File.createTempFile("baseform", "xlsx");
                Files.copy(file, tmp.toPath(), StandardCopyOption.REPLACE_EXISTING);
                wb = (XSSFWorkbook) WorkbookFactory.create(tmp);
            } else
                wb = new XSSFWorkbook();
            XSSFSheet sheet = wb.getSheet("Sheet1");
            int endCol = 0;
            for (Map<String, Object> list : titleInfo.get(0)) {
                endCol += list.get("colspan") == null ? 1 : Integer.parseInt((String) list.get("colspan"));
            }
            createTile(wb, sheet, titleName, endCol);
            createBaseInfo(wb, sheet, baseInfos, endCol);
            setTitleData(wb, sheet, false, titleInfo, 2 + baseInfos.length, endCol);
            List<Map<String, String>> colsValue = getColsValue(titleInfo);//获取每列对应的数据
            setTableData(wb, sheet, dataLists, colsValue, 2 + baseInfos.length + titleInfo.size());
            if (isMergeData == MERGE_DATA_CELL) {
                mergeCell(sheet, 2 + baseInfos.length + titleInfo.size(), mergeIndex, benchmarkColumn);
            }
            pictureToSheet(wb, sheet);
            return wb;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 表格数据信息为javabean类型，导出含图表的报表excel
     *
     * @param titleName       excel标题名
     * @param baseInfos       excel基础信息
     * @param titleInfo       表头信息
     * @param dataLists       表格数据信息
     * @param chartType       图表类型
     * @param chartDataLists  图表数据
     * @param isMergeData     是否需要合并表格单元格 MERGE_DATA_CELL(需要合并)/NOT_MERGE_DATA_CELL(不需要)
     * @param mergeIndex      需要合并列的索引
     * @param benchmarkColumn 基准列索引
     * @param <T>
     * @return
     */
    public static <T> XSSFWorkbook export(String titleName, String[][] baseInfos, List<List<Map<String, Object>>> titleInfo, Collection<T> dataLists, String chartType, String[][] chartDataLists, boolean isMergeData, int[] mergeIndex, Integer benchmarkColumn) {
        try {
            XSSFWorkbook wb = null;
            InputStream file = Resource.getResourceInputStream(templatePath);
            if (file != null) {
                File tmp = File.createTempFile("baseform", "xlsx");
                Files.copy(file, tmp.toPath(), StandardCopyOption.REPLACE_EXISTING);
                wb = (XSSFWorkbook) WorkbookFactory.create(tmp);
            } else
                wb = new XSSFWorkbook();
            XSSFSheet sheet = wb.getSheet("Sheet1");
            int endCol = 0;
            for (Map<String, Object> list : titleInfo.get(0)) {
                endCol += list.get("colspan") == null ? 1 : Integer.parseInt((String) list.get("colspan"));
            }
            createTile(wb, sheet, titleName, endCol);
            createBaseInfo(wb, sheet, baseInfos, endCol);
            setTitleData(wb, sheet, false, titleInfo, 8 + baseInfos.length + chartDataLists.length, endCol);
            List<Map<String, String>> colsValue = getColsValue(titleInfo);//获取每列对应的数据
            setTableData(wb, sheet, dataLists, colsValue, 8 + baseInfos.length + chartDataLists.length + titleInfo.size());
            if (PIE_CHART.equals(chartType)) {
                drawPieChart(sheet, "not3D", chartDataLists, baseInfos);
            } else if (HISTOGRAM_CHAR.equals(chartType)) {
                drawHistogramChart(sheet, STBarGrouping.CLUSTERED, chartDataLists, baseInfos);
            } else if (LINE_CHAR.equals(chartType)) {
                drawLineChart(sheet, "line", chartDataLists, baseInfos);
            }
            if (isMergeData == MERGE_DATA_CELL) {
                mergeCell(sheet, 8 + baseInfos.length + chartDataLists.length + titleInfo.size(), mergeIndex, benchmarkColumn);
            }

            pictureToSheet(wb, sheet);
            return wb;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 表格数据信息为map类型，导出不含图表的报表excel
     *
     * @param titleName       excel标题名
     * @param baseInfos       excel基础信息
     * @param titleInfo       表头信息
     * @param dataLists       表格数据信息
     * @param isMergeData     是否需要合并表格单元格 MERGE_DATA_CELL(需要合并)/NOT_MERGE_DATA_CELL(不需要)
     * @param mergeIndex      需要合并列的索引
     * @param benchmarkColumn 基准列索引
     * @return
     */
    public static XSSFWorkbook exportByMap(String titleName, String[][] baseInfos, List<List<Map<String, Object>>> titleInfo, Collection<Map<String, Object>> dataLists, boolean isMergeData, int[] mergeIndex, Integer benchmarkColumn) {
        try {
            XSSFWorkbook wb = null;
            InputStream file = Resource.getResourceInputStream(templatePath);
            if (file != null) {
                File tmp = File.createTempFile("baseform", "xlsx");
                Files.copy(file, tmp.toPath(), StandardCopyOption.REPLACE_EXISTING);
                wb = (XSSFWorkbook) WorkbookFactory.create(tmp);
            } else
                wb = new XSSFWorkbook();
            XSSFSheet sheet = wb.getSheet("Sheet1");
            int endCol = 0;
            for (Map<String, Object> list : titleInfo.get(0)) {
                endCol += list.get("colspan") == null ? 1 : Integer.parseInt((String) list.get("colspan"));
            }
            createTile(wb, sheet, titleName, endCol);
            createBaseInfo(wb, sheet, baseInfos, endCol);
            setTitleData(wb, sheet, false, titleInfo, 2 + baseInfos.length, endCol);
            List<Map<String, String>> colsValue = getColsValue(titleInfo);//获取每列对应的数据
            setTableDataByMap(wb, sheet, dataLists, colsValue, 2 + baseInfos.length + titleInfo.size());
            if (isMergeData == MERGE_DATA_CELL) {
                mergeCell(sheet, 2 + baseInfos.length + titleInfo.size(), mergeIndex, benchmarkColumn);
            }
            pictureToSheet(wb, sheet);
            return wb;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 表格数据信息为map类型，导出含图表的报表excel
     *
     * @param titleName       excel标题名
     * @param baseInfos       excel基础信息
     * @param titleInfo       表头信息
     * @param dataLists       表格数据信息
     * @param isMergeData     是否需要合并表格单元格 MERGE_DATA_CELL(需要合并)/NOT_MERGE_DATA_CELL(不需要)
     * @param chartType       图表类型
     * @param chartDataLists  图表数据
     * @param mergeIndex      需要合并列的索引
     * @param benchmarkColumn 基准列索引
     * @return
     */
    public static XSSFWorkbook exportByMap(String titleName, String[][] baseInfos, List<List<Map<String, Object>>> titleInfo, Collection<Map<String, Object>> dataLists, String chartType, String[][] chartDataLists, boolean isMergeData, int[] mergeIndex, Integer benchmarkColumn) {
        try {
            XSSFWorkbook wb = null;
            InputStream file = Resource.getResourceInputStream(templatePath);
            if (file != null) {
                File tmp = File.createTempFile("baseform", "xlsx");
                Files.copy(file, tmp.toPath(), StandardCopyOption.REPLACE_EXISTING);
                wb = (XSSFWorkbook) WorkbookFactory.create(tmp);
            } else
                wb = new XSSFWorkbook();
            XSSFSheet sheet = wb.getSheet("Sheet1");
            int endCol = 0;
            for (Map<String, Object> list : titleInfo.get(0)) {
                endCol += list.get("colspan") == null ? 1 : Integer.parseInt((String) list.get("colspan"));
            }
            createTile(wb, sheet, titleName, endCol);
            createBaseInfo(wb, sheet, baseInfos, endCol);
            setTitleData(wb, sheet, false, titleInfo, 8 + baseInfos.length + chartDataLists.length, endCol);
            List<Map<String, String>> colsValue = getColsValue(titleInfo);//获取每列对应的数据
            setTableDataByMap(wb, sheet, dataLists, colsValue, 8 + baseInfos.length + chartDataLists.length + titleInfo.size());
            if (PIE_CHART.equals(chartType)) {
                drawPieChart(sheet, "not3D", chartDataLists, baseInfos);
            } else if (HISTOGRAM_CHAR.equals(chartType)) {
                drawHistogramChart(sheet, STBarGrouping.CLUSTERED, chartDataLists, baseInfos);
            } else if (LINE_CHAR.equals(chartType)) {
                drawLineChart(sheet, "line", chartDataLists, baseInfos);
            }
            if (isMergeData == MERGE_DATA_CELL) {
                mergeCell(sheet, 8 + baseInfos.length + chartDataLists.length + titleInfo.size(), mergeIndex, benchmarkColumn);
            }
            pictureToSheet(wb, sheet);
            return wb;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 合并单元格
     *
     * @param sheet           sheet页对象
     * @param beginRow        开始行
     * @param mergeIndexs     需要合并列的索引
     * @param benchmarkColumn 基准列索引
     */
    private static void mergeCell(XSSFSheet sheet, int beginRow, int[] mergeIndexs, Integer benchmarkColumn) {
        if (sheet.getRow(beginRow)!=null){
            String thisCellValue = getCellValueByCell(sheet.getRow(beginRow).getCell(benchmarkColumn));
            int beginIndex = beginRow;
            int endIndex = beginRow;
            Map<Integer, Integer> benchmarkMap = new LinkedHashMap<>();
            for (int i = beginRow + 1; i <= sheet.getLastRowNum(); i++) {
                String value = getCellValueByCell(sheet.getRow(i).getCell(benchmarkColumn));
                if (thisCellValue.equals(value)) {
                    ++endIndex;
                    if (i == sheet.getLastRowNum()) {
                        sheet.addMergedRegion(new CellRangeAddress(beginIndex, endIndex, benchmarkColumn, benchmarkColumn));
                        benchmarkMap.put(beginIndex, endIndex);
                    }
                } else {
                    if (endIndex > beginIndex) {
                        sheet.addMergedRegion(new CellRangeAddress(beginIndex, endIndex, benchmarkColumn, benchmarkColumn));
                    }
                    benchmarkMap.put(beginIndex, endIndex);
                    if (i == sheet.getLastRowNum()) {
                        benchmarkMap.put(i, i);
                    } else {
                        thisCellValue = value;
                        beginIndex = i;
                        endIndex = i;
                    }
                }
            }

            for (int i = 0; i < mergeIndexs.length; i++) {
                if (mergeIndexs[i] != benchmarkColumn) {
                    Set<Map.Entry<Integer, Integer>> entries = benchmarkMap.entrySet();
                    for (Map.Entry<Integer, Integer> entry : entries) {
                        thisCellValue = getCellValueByCell(sheet.getRow(entry.getKey()).getCell(mergeIndexs[i]));
                        beginIndex = entry.getKey();
                        endIndex = entry.getKey();
                        for (int j = entry.getKey(); j <= entry.getValue(); j++) {
                            if (j == entry.getKey()) {
                                continue;
                            }
                            String value = getCellValueByCell(sheet.getRow(j).getCell(mergeIndexs[i]));
                            if (thisCellValue.equals(value)) {
                                ++endIndex;
                                if (j == entry.getValue()) {
                                    sheet.addMergedRegion(new CellRangeAddress(beginIndex, endIndex, mergeIndexs[i], mergeIndexs[i]));
                                }
                            } else {
                                if (endIndex > beginIndex) {
                                    sheet.addMergedRegion(new CellRangeAddress(beginIndex, endIndex, mergeIndexs[i], mergeIndexs[i]));
                                }
                                if (j != entry.getValue()) {
                                    thisCellValue = value;
                                    beginIndex = j;
                                    endIndex = j;
                                }
                            }
                        }
                    }
                }
            }
        }



    }


    /**
     * 填充表格数据
     *
     * @param wb         excel文本对象
     * @param sheet      sheet页对象
     * @param dataLists  数据
     * @param fieldArray 导出列
     * @param beginRow   开始行
     * @param <T>
     */
    public static <T> void setTableData(XSSFWorkbook wb, XSSFSheet sheet, Collection<T> dataLists, List<Map<String, String>> fieldArray, int beginRow) {
        try {
            XSSFCellStyle style = wb.createCellStyle();
            XSSFCellStyle intStyle = wb.createCellStyle();
            XSSFCellStyle doubleStyle = wb.createCellStyle();
            XSSFDataFormat df = wb.createDataFormat(); // 此处设置数据格式
            // 设置字体样式
            Font titleFont = wb.createFont();
            titleFont.setFontName("Times New Roman");// 字体样式

            style.setFont(titleFont);
            // 设置单元格对齐方式
            style.setAlignment(XSSFCellStyle.ALIGN_CENTER);// 水平居中
            style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);// 垂直居中

            intStyle.setFont(titleFont);
            // 设置单元格对齐方式
            intStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);// 水平居中
            intStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);// 垂直居中
            intStyle.setDataFormat(df.getFormat("0"));//数据格式只显示整数

            doubleStyle.setFont(titleFont);
            // 设置单元格对齐方式
            doubleStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);// 水平居中
            doubleStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);// 垂直居中
            doubleStyle.setDataFormat(df.getFormat("0.00"));//保留两位小数点
            // 遍历集合数据，产生数据行
            Iterator<T> it = dataLists.iterator();
            int index = beginRow;
            XSSFRow row;// 行数从0开始
            while (it.hasNext()) {

                row = sheet.createRow(index);
                T t = it.next();
                // 利用反射，根据传过来的字段名数组，动态调用对应的getXxx()方法得到属性值
                for (int i = 0; i < fieldArray.size(); i++) {
                    //需要序号就需要i+1
                    XSSFCell dataCell = row.createCell(i);
//                    dataCell.setCellStyle(style);
                    //需要序号就需要i+1
//                    sheet.autoSizeColumn(i);
                    String fieldName = fieldArray.get(i).get("titleValue");
                    String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);// 取得对应getXxx()方法
                    Class<?> tCls = t.getClass();// 泛型为Object以及所有Object的子类
                    Method getMethod = tCls.getMethod(getMethodName);// 通过方法名得到对应的方法
                    Object value = getMethod.invoke(t);// 动态调用方,得到属性值
                    boolean isNum;//data是否为数值型
                    boolean isInteger;//data是否为整数
                    boolean isPercent;//data是否为百分数
                    if (value != null) {
                        String thisValue = value.toString();
                        if (StringUtils.isNotBlank(fieldArray.get(i).get("changeValue"))) {
                            thisValue = DoubleOperationUtil.getBigDecimal(DoubleOperationUtil.mulString(thisValue , fieldArray.get(i).get("changeValue")));
                        }
                        //判断data是否为数值型
                        isNum = thisValue.matches("^(-?\\d*)(\\.\\d*[Ee]?-?\\d*)+$|^[-+]?[\\d]+$");
                        //判断data是否为整数（小数部分是否为0）
                        isInteger = thisValue.matches("^[-+]?[\\d]+$");
                        //判断data是否为百分数（是否包含“%”）
                        isPercent = thisValue.contains("%");
//                        dataCell.setCellValue(value.toString());// 为当前列赋值

                        //如果单元格内容是数值类型，涉及到金钱（金额、本、利），则设置cell的类型为数值型，设置data的类型为数值类型
                        if (isNum && !isPercent) {

                            if (isInteger) {
                                // 设置单元格格式
                                dataCell.setCellStyle(intStyle);
                            } else {
                                // 设置单元格格式
                                dataCell.setCellStyle(doubleStyle);
                            }

                            // 设置单元格内容为double类型
                            if (StringUtils.isNotBlank(thisValue)){
                                dataCell.setCellValue(Double.parseDouble(DoubleOperationUtil.getBigDecimal(thisValue)));
                            }else {
                                dataCell.setCellValue("");
                            }
                        } else {
                            dataCell.setCellStyle(style);
                            // 设置单元格内容为字符型
                            dataCell.setCellValue(thisValue);
                        }
                    }
                }
                ++index;// 0号位被占用 所以+1
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 填充表格数据
     *
     * @param wb         excel文本对象
     * @param sheet      sheet页对象
     * @param dataLists  数据
     * @param fieldArray 导出列
     * @param beginRow   开始行
     */
    public static void setTableDataByMap(XSSFWorkbook wb, XSSFSheet sheet, Collection<Map<String, Object>> dataLists, List<Map<String, String>> fieldArray, int beginRow) {
        try {
            XSSFCellStyle style = wb.createCellStyle();
            XSSFCellStyle intStyle = wb.createCellStyle();
            XSSFCellStyle doubleStyle = wb.createCellStyle();
            XSSFDataFormat df = wb.createDataFormat(); // 此处设置数据格式
            // 设置字体样式
            Font titleFont = wb.createFont();
            titleFont.setFontName("Times New Roman");// 字体样式

            style.setFont(titleFont);
            // 设置单元格对齐方式
            style.setAlignment(XSSFCellStyle.ALIGN_CENTER);// 水平居中
            style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);// 垂直居中

            intStyle.setFont(titleFont);
            // 设置单元格对齐方式
            intStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);// 水平居中
            intStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);// 垂直居中
            intStyle.setDataFormat(df.getFormat("0"));//数据格式只显示整数

            doubleStyle.setFont(titleFont);
            // 设置单元格对齐方式
            doubleStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);// 水平居中
            doubleStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);// 垂直居中
            doubleStyle.setDataFormat(df.getFormat("0.00"));//保留两位小数点
            // 遍历集合数据，产生数据行
            Iterator<Map<String, Object>> it = dataLists.iterator();
            int index = beginRow;
            XSSFRow row;// 行数从0开始
            while (it.hasNext()) {

                row = sheet.createRow(index);
                Map next = it.next();
                for (int i = 0; i < fieldArray.size(); i++) {
                    XSSFCell dataCell = row.createCell(i);
                    dataCell.setCellStyle(style);
                    String fieldName = fieldArray.get(i).get("titleValue");
                    Object value = next.get(fieldName);
                    boolean isNum;//data是否为数值型
                    boolean isInteger;//data是否为整数
                    boolean isPercent;//data是否为百分数
                    if (value != null) {
                        String thisValue = value.toString();
                        //判断data是否为数值型
                        isNum = thisValue.matches("^(-?\\d*)(\\.\\d*[Ee]?-?\\d*)+$|^[-+]?[\\d]+$");
                        //判断data是否为整数（小数部分是否为0）
                        isInteger = thisValue.matches("^[-+]?[\\d]+$");
                        //判断data是否为百分数（是否包含“%”）
                        isPercent = thisValue.contains("%");
//                        dataCell.setCellValue(value.toString());// 为当前列赋值

                        //如果单元格内容是数值类型，涉及到金钱（金额、本、利），则设置cell的类型为数值型，设置data的类型为数值类型
                        if (isNum && !isPercent) {
                            if (isInteger) {
                                // 设置单元格格式
                                dataCell.setCellStyle(intStyle);
                            } else {
                                // 设置单元格格式
                                dataCell.setCellStyle(doubleStyle);
                            }

                            // 设置单元格内容为double类型
                            if (StringUtils.isNotBlank(thisValue)){
                                dataCell.setCellValue(Double.parseDouble(DoubleOperationUtil.getBigDecimal(thisValue)));
                            }else {
                                dataCell.setCellValue("");
                            }
                        } else {
                            dataCell.setCellStyle(style);
                            // 设置单元格内容为字符型
                            dataCell.setCellValue(thisValue);
                        }
                    }
                }
                ++index;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 生成饼图
     *
     * @param sheet          sheet页对象
     * @param type           图类型(3D或者普通)
     * @param chartDataLists 饼图数据
     */
    public static void drawPieChart(XSSFSheet sheet, String type, String[][] chartDataLists, String[][] baseInfos) {
        // 获取sheet名称
        String sheetName = sheet.getSheetName();
        drawChartTable(sheet, chartDataLists, baseInfos);
        // 创建一个画布
        Drawing drawing = sheet.createDrawingPatriarch();
        // 画一个图区域
        ClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, 0, 1 + baseInfos.length, 5, 1 + baseInfos.length + chartDataLists.length + 6);
        // 创建一个chart对象
        Chart chart = drawing.createChart(anchor);
        CTChart ctChart = ((XSSFChart) chart).getCTChart();
        CTPlotArea ctPlotArea = ctChart.getPlotArea();
        CTBoolean ctBoolean = null;
        CTPie3DChart ctPie3DChart = null;
        CTPieChart ctPieChart = null;
        // 创建饼状图模型
        if (type.equals("is3D")) {
            ctPie3DChart = ctPlotArea.addNewPie3DChart();
            ctBoolean = ctPie3DChart.addNewVaryColors();
        } else {
            ctPieChart = ctPlotArea.addNewPieChart();
            ctBoolean = ctPieChart.addNewVaryColors();
        }
        // 创建序列,并且设置选中区域
        for (int i = 0; i < chartDataLists[0].length - 1; i++) {
            CTPieSer ctPieSer = null;
            if (type.equals("is3D")) {
                ctPieSer = ctPie3DChart.addNewSer();
            } else {
                ctPieSer = ctPieChart.addNewSer();
            }
            CTSerTx ctSerTx = ctPieSer.addNewTx();
            // 图例区
            CTStrRef ctStrRef = ctSerTx.addNewStrRef();
            // 标题
            String legendDataRange = new CellRangeAddress(1 + baseInfos.length, 1 + baseInfos.length, i + 1, i + 1).formatAsString(sheetName, true);
            ctStrRef.setF(legendDataRange);
            ctPieSer.addNewIdx().setVal(i);
            // 横坐标区
            CTAxDataSource cttAxDataSource = ctPieSer.addNewCat();
            ctStrRef = cttAxDataSource.addNewStrRef();

            if (chartDataLists.length>1){
                // 图例
                String axisDataRange = new CellRangeAddress(2 + baseInfos.length, baseInfos.length + chartDataLists.length, 0, 0).formatAsString(sheetName, true);
                ctStrRef.setF(axisDataRange);
                CTNumDataSource ctNumDataSource = ctPieSer.addNewVal();
                CTNumRef ctNumRef = ctNumDataSource.addNewNumRef();
                // 数据区域
                String numDataRange = new CellRangeAddress(2 + baseInfos.length, baseInfos.length + chartDataLists.length, i + 1, i + 1).formatAsString(sheetName,
                        true);
                ctNumRef.setF(numDataRange);
//            // 显示边框线
//            ctPieSer.addNewSpPr().addNewLn().addNewSolidFill().addNewSrgbClr().setVal(new byte[]{0, 0, 0});
                // 设置标签格式
                CTDLbls newDLbls = ctPieSer.addNewDLbls();
                ctBoolean.setVal(true);
                newDLbls.setShowVal(ctBoolean);
                newDLbls.setShowPercent(ctBoolean);
            }
        }
        // legend图注
        CTLegend ctLegend = ctChart.addNewLegend();
        ctLegend.addNewLegendPos().setVal(STLegendPos.TR);
    }

    /**
     * 生成柱状图
     *
     * @param sheet          sheet页对象
     * @param chartDataLists 饼图数据
     */
    private static void drawHistogramChart(XSSFSheet sheet, STBarGrouping.Enum group, String[][] chartDataLists, String[][] baseInfos) {
        // 获取sheet名称
        String sheetName = sheet.getSheetName();
        drawChartTable(sheet, chartDataLists, baseInfos);
        // 创建一个画布
        Drawing drawing = sheet.createDrawingPatriarch();
        // 画一个图区域
        ClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, 0, 1 + baseInfos.length, 5, 1 + baseInfos.length + chartDataLists.length + 6);
        // 创建一个chart对象
        Chart chart = drawing.createChart(anchor);
        CTChart ctChart = ((XSSFChart) chart).getCTChart();
        CTPlotArea ctPlotArea = ctChart.getPlotArea();
        // 创建柱状图模型
        CTBarChart ctBarChart = ctPlotArea.addNewBarChart();
        CTBoolean ctBoolean = ctBarChart.addNewVaryColors();
        ctBarChart.getVaryColors().setVal(true);
        // 设置图类型
        ctBarChart.addNewGrouping().setVal(group);
        ctBoolean.setVal(true);
        ctBarChart.addNewBarDir().setVal(STBarDir.COL);
        // 是否添加左侧坐标轴
        ctChart.addNewDispBlanksAs().setVal(STDispBlanksAs.ZERO);
        ctChart.addNewShowDLblsOverMax().setVal(true);
        // 设置这两个参数是为了在STACKED模式下生成堆积模式；(standard)标准模式时需要将这两行去掉
        if ("stacked".equals(group.toString()) || "percentStacked".equals(group.toString())) {
            ctBarChart.addNewGapWidth().setVal(150);
            ctBarChart.addNewOverlap().setVal((byte) 100);
        }
        // 创建序列,并且设置选中区域
        for (int i = 0; i < chartDataLists[0].length - 1; i++) {
            CTBarSer ctBarSer = ctBarChart.addNewSer();
            CTSerTx ctSerTx = ctBarSer.addNewTx();
            // 图例区
            CTStrRef ctStrRef = ctSerTx.addNewStrRef();
            // 标题
            String legendDataRange = new CellRangeAddress(1 + baseInfos.length, 1 + baseInfos.length, i + 1, i + 1).formatAsString(sheetName, true);
            ctStrRef.setF(legendDataRange);
            ctBarSer.addNewIdx().setVal(i);
            // 横坐标区
            CTAxDataSource cttAxDataSource = ctBarSer.addNewCat();
            ctStrRef = cttAxDataSource.addNewStrRef();
            if (chartDataLists.length>1) {
                // 图例
                String axisDataRange = new CellRangeAddress(2 + baseInfos.length, baseInfos.length + chartDataLists.length, 0, 0).formatAsString(sheetName, true);
                ctStrRef.setF(axisDataRange);
                // 数据区域
                CTNumDataSource ctNumDataSource = ctBarSer.addNewVal();
                CTNumRef ctNumRef = ctNumDataSource.addNewNumRef();
                // 数据区域
                String numDataRange = new CellRangeAddress(2 + baseInfos.length, baseInfos.length + chartDataLists.length, i + 1, i + 1).formatAsString(sheetName,
                        true);
                ctNumRef.setF(numDataRange);
            }
            // 添加柱状边框线
            ctBarSer.addNewSpPr().addNewLn().addNewSolidFill().addNewSrgbClr().setVal(new byte[]{0, 0, 0});
            // 设置负轴颜色不是白色
            ctBarSer.addNewInvertIfNegative().setVal(false);
            // 设置标签格式
            ctBoolean.setVal(false);
            CTDLbls newDLbls = ctBarSer.addNewDLbls();
            newDLbls.setShowLegendKey(ctBoolean);
            ctBoolean.setVal(true);
            newDLbls.setShowVal(ctBoolean);
            ctBoolean.setVal(false);
            newDLbls.setShowCatName(ctBoolean);
            newDLbls.setShowSerName(ctBoolean);
            newDLbls.setShowPercent(ctBoolean);
            newDLbls.setShowBubbleSize(ctBoolean);
            newDLbls.setShowLeaderLines(ctBoolean);
        }
        // 告诉BarChart它有坐标轴，并给它们id
        ctBarChart.addNewAxId().setVal(123456);
        ctBarChart.addNewAxId().setVal(123457);
        // 横坐标
        CTCatAx ctCatAx = ctPlotArea.addNewCatAx();
        ctCatAx.addNewAxId().setVal(123456); // id of the cat axis
        CTScaling ctScaling = ctCatAx.addNewScaling();
        ctScaling.addNewOrientation().setVal(STOrientation.MIN_MAX);
        ctCatAx.addNewAxPos().setVal(STAxPos.B);
        ctCatAx.addNewCrossAx().setVal(123457); // id of the val axis
        ctCatAx.addNewTickLblPos().setVal(STTickLblPos.NEXT_TO);
        // 纵坐标
        CTValAx ctValAx = ctPlotArea.addNewValAx();
        ctValAx.addNewAxId().setVal(123457); // id of the val axis
        ctScaling = ctValAx.addNewScaling();
        ctScaling.addNewOrientation().setVal(STOrientation.MIN_MAX);
        // 设置位置
        ctValAx.addNewAxPos().setVal(STAxPos.L);
        ctValAx.addNewCrossAx().setVal(123456); // id of the cat axis
        ctValAx.addNewTickLblPos().setVal(STTickLblPos.NEXT_TO);
        // 是否删除主左边轴
        ctValAx.addNewDelete().setVal(false);
        // 是否删除横坐标
        ctCatAx.addNewDelete().setVal(false);
        // legend图注
        // if(true){
        CTLegend ctLegend = ctChart.addNewLegend();
        ctLegend.addNewLegendPos().setVal(STLegendPos.B);
        ctLegend.addNewOverlay().setVal(false);
        // }
    }

    /**
     * 生成折线图
     *
     * @param sheet          sheet页对象
     * @param type           图类型(3D或者普通)
     * @param chartDataLists 饼图数据
     */
    private static void drawLineChart(XSSFSheet sheet, String type, String[][] chartDataLists, String[][] baseInfos) {
        // 获取sheet名称
        String sheetName = sheet.getSheetName();
        drawChartTable(sheet, chartDataLists, baseInfos);
        // 创建一个画布
        Drawing drawing = sheet.createDrawingPatriarch();
        // 画一个图区域
        ClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, 0, 1 + baseInfos.length, 5, 1 + baseInfos.length + chartDataLists.length + 6);
        // 创建一个chart对象
        Chart chart = drawing.createChart(anchor);
        CTChart ctChart = ((XSSFChart) chart).getCTChart();
        CTPlotArea ctPlotArea = ctChart.getPlotArea();
        if (type.equals("line-bar")) {
            CTBarChart ctBarChart = ctPlotArea.addNewBarChart();
            CTBoolean ctBoolean = ctBarChart.addNewVaryColors();
            ctBarChart.getVaryColors().setVal(true);
            // 设置类型
            ctBarChart.addNewGrouping().setVal(STBarGrouping.CLUSTERED);
            ctBoolean.setVal(true);
            ctBarChart.addNewBarDir().setVal(STBarDir.COL);
            // 是否添加左侧坐标轴
            ctChart.addNewDispBlanksAs().setVal(STDispBlanksAs.ZERO);
            ctChart.addNewShowDLblsOverMax().setVal(true);
            // telling the BarChart that it has axes and giving them Ids
            ctBarChart.addNewAxId().setVal(123456);
            ctBarChart.addNewAxId().setVal(123457);
            // cat axis
            CTCatAx ctCatAx = ctPlotArea.addNewCatAx();
            ctCatAx.addNewAxId().setVal(123456); // id of the cat axis
            CTScaling ctScaling = ctCatAx.addNewScaling();
            ctScaling.addNewOrientation().setVal(STOrientation.MIN_MAX);
            ctCatAx.addNewAxPos().setVal(STAxPos.B);
            ctCatAx.addNewCrossAx().setVal(123457); // id of the val axis
            ctCatAx.addNewTickLblPos().setVal(STTickLblPos.NEXT_TO);
            // val axis
            CTValAx ctValAx = ctPlotArea.addNewValAx();
            ctValAx.addNewAxId().setVal(123457); // id of the val axis
            ctScaling = ctValAx.addNewScaling();
            ctScaling.addNewOrientation().setVal(STOrientation.MIN_MAX);
            ctValAx.addNewAxPos().setVal(STAxPos.L);
            ctValAx.addNewCrossAx().setVal(123456); // id of the cat axis
            ctValAx.addNewTickLblPos().setVal(STTickLblPos.NEXT_TO);
        }
        // 折线图
        CTLineChart ctLineChart = ctPlotArea.addNewLineChart();
        CTBoolean ctBoolean = ctLineChart.addNewVaryColors();
        ctLineChart.addNewGrouping().setVal(STGrouping.STANDARD);
        // 创建序列,并且设置选中区域
        for (int i = 0; i < chartDataLists[0].length - 1; i++) {
            CTLineSer ctLineSer = ctLineChart.addNewSer();
            CTSerTx ctSerTx = ctLineSer.addNewTx();
            // 图例区
            CTStrRef ctStrRef = ctSerTx.addNewStrRef();
            // 标题
            String legendDataRange = new CellRangeAddress(1 + baseInfos.length, 1 + baseInfos.length, i + 1, i + 1).formatAsString(sheetName, true);
            ctStrRef.setF(legendDataRange);
            ctLineSer.addNewIdx().setVal(i);
            // 横坐标区
            CTAxDataSource cttAxDataSource = ctLineSer.addNewCat();
            ctStrRef = cttAxDataSource.addNewStrRef();
            if (chartDataLists.length>1) {
                // 图例
                String axisDataRange = new CellRangeAddress(2 + baseInfos.length, baseInfos.length + chartDataLists.length, 0, 0).formatAsString(sheetName, true);
                ctStrRef.setF(axisDataRange);
                // 数据区域
                CTNumDataSource ctNumDataSource = ctLineSer.addNewVal();
                CTNumRef ctNumRef = ctNumDataSource.addNewNumRef();
                // 数据区域
                String numDataRange = new CellRangeAddress(2 + baseInfos.length, baseInfos.length + chartDataLists.length, i + 1, i + 1).formatAsString(sheetName,
                        true);
                ctNumRef.setF(numDataRange);
            }
            // 设置标签格式
            ctBoolean.setVal(false);
            CTDLbls newDLbls = ctLineSer.addNewDLbls();
            newDLbls.setShowLegendKey(ctBoolean);
            ctBoolean.setVal(true);
            newDLbls.setShowVal(ctBoolean);
            ctBoolean.setVal(false);
            newDLbls.setShowCatName(ctBoolean);
            newDLbls.setShowSerName(ctBoolean);
            newDLbls.setShowPercent(ctBoolean);
            newDLbls.setShowBubbleSize(ctBoolean);
            newDLbls.setShowLeaderLines(ctBoolean);
            // 是否是平滑曲线
            CTBoolean addNewSmooth = ctLineSer.addNewSmooth();
            addNewSmooth.setVal(false);
            // 是否是堆积曲线
            CTMarker addNewMarker = ctLineSer.addNewMarker();
            CTMarkerStyle addNewSymbol = addNewMarker.addNewSymbol();
            addNewSymbol.setVal(STMarkerStyle.NONE);
        }
        // telling the BarChart that it has axes and giving them Ids
        ctLineChart.addNewAxId().setVal(123456);
        ctLineChart.addNewAxId().setVal(123457);
        // cat axis
        CTCatAx ctCatAx = ctPlotArea.addNewCatAx();
        ctCatAx.addNewAxId().setVal(123456); // id of the cat axis
        CTScaling ctScaling = ctCatAx.addNewScaling();
        ctScaling.addNewOrientation().setVal(STOrientation.MIN_MAX);
        ctCatAx.addNewAxPos().setVal(STAxPos.B);
        ctCatAx.addNewCrossAx().setVal(123457); // id of the val axis
        ctCatAx.addNewTickLblPos().setVal(STTickLblPos.NEXT_TO);
        // val axis
        CTValAx ctValAx = ctPlotArea.addNewValAx();
        ctValAx.addNewAxId().setVal(123457); // id of the val axis
        ctScaling = ctValAx.addNewScaling();
        ctScaling.addNewOrientation().setVal(STOrientation.MIN_MAX);
        ctValAx.addNewAxPos().setVal(STAxPos.L);
        ctValAx.addNewCrossAx().setVal(123456); // id of the cat axis
        ctValAx.addNewTickLblPos().setVal(STTickLblPos.NEXT_TO);
        // 是否删除主左边轴
        ctValAx.addNewDelete().setVal(false);

        // 是否删除横坐标
        ctCatAx.addNewDelete().setVal(false);
        CTLegend ctLegend = ctChart.addNewLegend();
        ctLegend.addNewLegendPos().setVal(STLegendPos.B);
        ctLegend.addNewOverlay().setVal(false);
    }

    /**
     * 生成图标数据表
     *
     * @param sheet          sheet页对象
     * @param chartDataLists 饼图数据
     */
    public static void drawChartTable(XSSFSheet sheet, String[][] chartDataLists, String[][] baseInfos) {
        // 填充数据
        for (int i = 0; i < chartDataLists.length; i++) {
            // 获取每一项的数据
            // 设置每一行的字段标题和数据
            XSSFRow rowi = sheet.createRow(1 + baseInfos.length + i);
            for (int j = 0; j < chartDataLists[i].length; j++) {
                // 判断是否是标题字段列
                if (i == 0) {
                    rowi.createCell(j).setCellValue(chartDataLists[i][j]);
                } else {
                    if (j == 0) {
                        rowi.createCell(j).setCellValue(chartDataLists[i][j]);
                    } else {
                        rowi.createCell(j).setCellValue(Double.parseDouble(chartDataLists[i][j]));
                    }

                }
            }
        }
    }


    /**
     * 填充表头数据
     *
     * @param wb         excel文本对象
     * @param sheet      sheet页对象
     * @param isRead     是否可读
     * @param titleInfos 表头数据
     * @param beginRow   开始行
     * @param endCol     最后一列索引
     */
    public static void setTitleData(XSSFWorkbook wb, XSSFSheet sheet, boolean isRead, List<List<Map<String, Object>>> titleInfos, int beginRow, int endCol) {
        try {
            List<Excel.CellBean> beans = getTitleData(titleInfos, beginRow, endCol);
            if (beans.size() >= 1) {
                for (Excel.CellBean bean1 : beans) {
                    if (bean1.getType() == 7) {
                        continue;
                    }
                    if (isRead) {
                        sheet.protectSheet(UUID.randomUUID().toString());
                        sheet.lockSelectLockedCells(true);
                        sheet.lockSelectUnlockedCells(true);
                    }
                    add(bean1, sheet, wb);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取表头解析后的数据
     *
     * @param titleInfos table数据
     * @param beginRow   开始行
     * @param endCol     最后一列索引
     * @return
     */
    private static List<Excel.CellBean> getTitleData(List<List<Map<String, Object>>> titleInfos, int beginRow, int endCol) {
        List<Excel.CellBean> cellBeans = new ArrayList<>();
        int col;
        int row = beginRow;
        List<Integer> temp = new ArrayList<>();
        for (int i = 0; i < endCol; i++) {
            temp.add(-1);
        }
        Excel.CellBean cellBean1 = new Excel.CellBean();
        cellBean1.setValue("Sheet1");
        cellBean1.setType(7);
        cellBeans.add(cellBean1);
        for (int i = 0; i < titleInfos.size(); i++) {
            col = 0;
            List<Map<String, Object>> dataList = titleInfos.get(i);
            for (Map<String, Object> data : dataList) {
                String value = (String) data.get("titleName");
                int rowspan = data.get("rowspan") == null ? 1 : Integer.parseInt((String) data.get("rowspan"));
                int colspan = data.get("colspan") == null ? 1 : Integer.parseInt((String) data.get("colspan"));
                int drow = rowspan - 1;
                int dcol = colspan - 1;
                for (int k = col; k < temp.size(); k++) {
                    if (temp.get(k) >= i) {
                        ++col;
                    } else {
                        break;
                    }
                }
                for (int j = col; j < col + colspan; j++) {
                    temp.set(j, temp.get(j) + rowspan);
                }
                Excel.CellBean cellBean = new Excel.CellBean();
                cellBean.setValue(value);
                cellBean.setRow(row);
                cellBean.setCol(col);
                cellBean.setDrow(drow);
                cellBean.setDcol(dcol);
                cellBeans.add(cellBean);
                col = col + colspan;
            }
            ++row;
        }
        return cellBeans;
    }

    /**
     * 获取每列对应的字段
     *
     * @param titleInfos 表头数据
     * @return
     */
    public static List<Map<String, String>> getColsValue(List<List<Map<String, Object>>> titleInfos) {
        int endCol = 0;
        for (Map<String, Object> list : titleInfos.get(0)) {
            endCol += list.get("colspan") == null ? 1 : Integer.parseInt((String) list.get("colspan"));
        }
        List<Map<String, String>> colsValue = new ArrayList<>();
//        String[] colsValue = new String[endCol];
        int col;
        int row = 0;
        Map<String, String> newMap = new HashMap<>();
        List<Integer> temp = new ArrayList<>();
        for (int i = 0; i < endCol; i++) {
            temp.add(-1);
            colsValue.add(newMap);
        }
        for (int i = 0; i < titleInfos.size(); i++) {
            col = 0;
            List<Map<String, Object>> dataList = titleInfos.get(i);
            for (Map<String, Object> data : dataList) {
                String value = (String) data.get("titleValue");
                int rowspan = data.get("rowspan") == null ? 1 : Integer.parseInt((String) data.get("rowspan"));
                int colspan = data.get("colspan") == null ? 1 : Integer.parseInt((String) data.get("colspan"));

                for (int k = col; k < temp.size(); k++) {
                    if (temp.get(k) >= i) {
                        ++col;
                    } else {
                        break;
                    }
                }
                if (temp.get(col) + rowspan >= titleInfos.size() - 1) {
//                        colsValue[col] = value;
                    Map<String, String> map = new HashMap<>();
                    map.put("titleValue", value);
                    map.put("changeValue", data.get("changeValue") == null ? null : (String) data.get("changeValue"));
                    colsValue.set(col, map);
                }
                for (int j = col; j < col + colspan; j++) {
                    temp.set(j, temp.get(j) + rowspan);
                }
                col = col + colspan;
            }
            ++row;
        }
        return colsValue;
    }

    /**
     * 添加单元个的功能
     *
     * @param bean  数据单元格
     * @param sheet 表单
     * @param wb    文件
     */
    private static void add(Excel.CellBean bean, XSSFSheet sheet, XSSFWorkbook wb) {
        XSSFCellStyle style = wb.createCellStyle();
        // 设置字体样式
        Font titleFont = wb.createFont();
        titleFont.setFontName("Times New Roman");// 字体样式
        style.setFont(titleFont);
        // 设置单元格对齐方式
        style.setAlignment(XSSFCellStyle.ALIGN_CENTER);// 水平居中
        style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);// 垂直居中
        if (bean == null)
            return;
        if (bean.getDrow() > 0 || bean.getDcol() > 0) {
            sheet.addMergedRegion(new CellRangeAddress(bean.getRow(), bean.getRow() + bean.getDrow(), bean.getCol(), bean.getCol() + bean.getDcol()));
        }
        XSSFRow row = sheet.getRow(bean.getRow());
        if (row == null)
            row = sheet.createRow(bean.getRow());
        XSSFCell cell = row.getCell(bean.getCol());
        if (cell == null)
            cell = row.createCell(bean.getCol());
        cell.setCellStyle(style);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            switch (bean.getType()) {
                case Excel.CellBean.TYPE_DOUBLE:
                    cell.setCellValue(Double.parseDouble(bean.getValue()));
                    break;
                case Excel.CellBean.TYPE_DATE:
                    cell.setCellValue(df.parse(bean.getValue()));
                    break;
                case Excel.CellBean.TYPE_BOOLEAN:
                    cell.setCellValue(bean.getValue().equalsIgnoreCase("true"));
                    break;
                default:
                    cell.setCellValue(bean.getValue());
                    break;
            }
        } catch (NumberFormatException | ParseException e) {
            e.printStackTrace();
        }

    }


    /**
     * 填充excel标题
     *
     * @param wb        excel文本对象
     * @param sheet     sheet页对象
     * @param titleName excel标题
     * @param endCol    最后一列
     */
    public static void createTile(XSSFWorkbook wb, XSSFSheet sheet, String titleName, int endCol) {
        XSSFCellStyle style = wb.createCellStyle();
        // 设置字体样式
        Font titleFont = wb.createFont();
        titleFont.setFontName("Times New Roman");// 字体样式
        titleFont.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);//粗体显示
        style.setFont(titleFont);
        // 设置单元格对齐方式
        style.setAlignment(XSSFCellStyle.ALIGN_CENTER);// 水平居中
        style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);// 垂直居中
        XSSFRow row = sheet.createRow(0);
        XSSFCell cell = row.createCell(endCol / 2);
        cell.setCellStyle(style);
        cell.setCellValue(titleName);
    }

    /**
     * 填充excel基本信息
     *
     * @param wb        excel文本对象
     * @param sheet     sheet页对象
     * @param baseInfos excel基本信息数据
     * @param endCol    最后一列
     */
    public static void createBaseInfo(XSSFWorkbook wb, XSSFSheet sheet, String[][] baseInfos, int endCol) {
        XSSFCellStyle leftStyle = wb.createCellStyle();
        XSSFCellStyle rightStyle = wb.createCellStyle();
        // 设置字体样式
        Font titleFont = wb.createFont();
        titleFont.setFontName("Times New Roman");// 字体样式
        leftStyle.setFont(titleFont);
        rightStyle.setFont(titleFont);
        // 设置单元格对齐方式
        leftStyle.setAlignment(XSSFCellStyle.ALIGN_LEFT);// 水平居中
        rightStyle.setAlignment(XSSFCellStyle.ALIGN_RIGHT);
        leftStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);// 垂直居中
        rightStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);// 垂直居中
        for (int i = 1; i <= baseInfos.length; i++) {
            XSSFRow baseRow = sheet.createRow(i);
            XSSFCell cell1 = baseRow.createCell(0);
            XSSFCell cell2 = baseRow.createCell(endCol - 1);
            cell1.setCellStyle(leftStyle);
            cell2.setCellStyle(rightStyle);
            cell1.setCellValue(baseInfos[i - 1][0]);
            cell2.setCellValue(baseInfos[i - 1][1]);
        }
    }

    /**
     * 导出图片logo
     *
     * @param wb    excel文本对象
     * @param sheet sheet页对象
     */
    public static void pictureToSheet(XSSFWorkbook wb, XSSFSheet sheet) {
        try {
            XSSFDrawing patriarch = sheet.createDrawingPatriarch();
            XSSFClientAnchor anchor = new XSSFClientAnchor(0, 0, 0, 0, 0, 0, 2, 1);
            InputStream inputStream = Resource.getResourceInputStream("static/images/reportLogo.png");
            if (inputStream != null) {
                int pictureIndex = wb.addPicture(inputStream, XSSFWorkbook.PICTURE_TYPE_PNG);
                patriarch.createPicture(anchor, pictureIndex);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取单元格各类型值，返回字符串类型
     *
     * @param cell
     * @return
     */
    private static String getCellValueByCell(Cell cell) {
        //判断是否为null或空串
        if (cell == null || cell.toString().trim().equals("")) {
            return "";
        }
        String cellValue = "";
        int cellType = cell.getCellType();
        if (cellType == Cell.CELL_TYPE_FORMULA) { //表达式类型
            cellType = evaluator.evaluate(cell).getCellType();
        }

        switch (cellType) {
            case Cell.CELL_TYPE_STRING: //字符串类型
                cellValue = cell.getStringCellValue().trim();
                cellValue = StringUtils.isEmpty(cellValue) ? "" : cellValue;
                break;
            case Cell.CELL_TYPE_BOOLEAN:  //布尔类型
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_NUMERIC: //数值类型
                if (DateUtil.isCellDateFormatted(cell)) {  //判断日期类型
                    cellValue = DateTimeUtil.formatDateTimetoString(cell.getDateCellValue(), "yyyy-MM-dd");
                } else {  //否
                    cellValue = new DecimalFormat("#.##").format(cell.getNumericCellValue());
                }
                break;
            default:
                cellValue = "";
                break;
        }
        return cellValue;
    }


    /**
     * 表格数据信息为javabean类型，导出不含图表，报表信息的报表
     *
     * @param titleName       excel标题名
     * @param baseInfos       excel基础信息
     * @param titleInfo       表头信息
     * @param dataLists       表格数据信息
     * @param isMergeData     是否需要合并表格单元格 MERGE_DATA_CELL(需要合并)/NOT_MERGE_DATA_CELL(不需要)
     * @param mergeIndex      需要合并列的索引
     * @param benchmarkColumn 基准列索引
     * @param <T>
     * @return
     */
    public static <T> XSSFWorkbook exportBaseTableByList(String titleName, List<List<Map<String, Object>>> titleInfo, Collection<T> dataLists, boolean isMergeData, int[] mergeIndex, Integer benchmarkColumn) {
        try {
            XSSFWorkbook wb = null;
            InputStream file = Resource.getResourceInputStream(templatePath);
            if (file != null) {
                File tmp = File.createTempFile("baseform", "xlsx");
                Files.copy(file, tmp.toPath(), StandardCopyOption.REPLACE_EXISTING);
                wb = (XSSFWorkbook) WorkbookFactory.create(tmp);
            } else
                wb = new XSSFWorkbook();
            XSSFSheet sheet = wb.getSheet("Sheet1");
            int endCol = 0;
            for (Map<String, Object> list : titleInfo.get(0)) {
                endCol += list.get("colspan") == null ? 1 : Integer.parseInt((String) list.get("colspan"));
            }
            setTitleData(wb, sheet, false, titleInfo, 0, endCol);
            List<Map<String, String>> colsValue = getColsValue(titleInfo);//获取每列对应的数据
            setTableData(wb, sheet, dataLists, colsValue, titleInfo.size());
            if (isMergeData == MERGE_DATA_CELL) {
                mergeCell(sheet, titleInfo.size(), mergeIndex, benchmarkColumn);
            }
            return wb;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 表格数据信息为map类型，导出不含图表的报表excel
     *
     * @param titleName       excel标题名
     * @param baseInfos       excel基础信息
     * @param titleInfo       表头信息
     * @param dataLists       表格数据信息
     * @param isMergeData     是否需要合并表格单元格 MERGE_DATA_CELL(需要合并)/NOT_MERGE_DATA_CELL(不需要)
     * @param mergeIndex      需要合并列的索引
     * @param benchmarkColumn 基准列索引
     * @return
     */
    public static XSSFWorkbook exportBaseTableByMap(String titleName, List<List<Map<String, Object>>> titleInfo, Collection<Map<String, Object>> dataLists, boolean isMergeData, int[] mergeIndex, Integer benchmarkColumn) {
        try {
            XSSFWorkbook wb = null;
            InputStream file = Resource.getResourceInputStream(templatePath);
            if (file != null) {
                File tmp = File.createTempFile("baseform", "xlsx");
                Files.copy(file, tmp.toPath(), StandardCopyOption.REPLACE_EXISTING);
                wb = (XSSFWorkbook) WorkbookFactory.create(tmp);
            } else
                wb = new XSSFWorkbook();
            XSSFSheet sheet = wb.getSheet("Sheet1");
            int endCol = 0;
            for (Map<String, Object> list : titleInfo.get(0)) {
                endCol += list.get("colspan") == null ? 1 : Integer.parseInt((String) list.get("colspan"));
            }
            setTitleData(wb, sheet, false, titleInfo, 0, endCol);
            List<Map<String, String>> colsValue = getColsValue(titleInfo);//获取每列对应的数据
            setTableDataByMap(wb, sheet, dataLists, colsValue, titleInfo.size());
            if (isMergeData == MERGE_DATA_CELL) {
                mergeCell(sheet, titleInfo.size(), mergeIndex, benchmarkColumn);
            }
            return wb;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
