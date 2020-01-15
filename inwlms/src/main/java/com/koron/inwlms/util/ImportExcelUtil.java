package com.koron.inwlms.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: ligui
 * @CreateTime: 2019-09-28 15:22
 * @Description: 导入excel
 */
public class ImportExcelUtil {
    /**
     * excel字段对应注解
     */
    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public @interface ExcelColumn {
        String value();
    }

    /**
     * excel字段对应注解
     */
    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public @interface RequiredColumn {
    }

    /**
     * 获取excel数据  将之转换成bean
     *
     * @param file
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> List<T> readExcel(MultipartFile file, Class<T> cls) {
        List<T> dataList = new ArrayList<>();
        String path = file.getOriginalFilename();
        Workbook workbook = null;
        AtomicBoolean flag = new AtomicBoolean(true);
        try {
            if (path.endsWith("xlsx")) {
                InputStream is = file.getInputStream();
                workbook = new XSSFWorkbook(is);
            }
            if (path.endsWith("xls")) {
                InputStream is = file.getInputStream();
                workbook = new HSSFWorkbook(is);
            }
            if (workbook != null) {
                //类映射
                Map<String, List<Field>> classMap = new HashMap<>();
                Field[] fields = cls.getDeclaredFields();
                for (Field field : fields) {
                    ExcelColumn annotation = field.getAnnotation(ExcelColumn.class);
                    if (annotation != null) {
                        String value = annotation.value();
                        if (!classMap.containsKey(value)) {
                            classMap.put(value, new ArrayList<>());
                        }
                        field.setAccessible(true);
                        classMap.get(value).add(field);
                    }
                }
                Map<Integer, List<Field>> reflectionMap = new HashMap<>();
                Sheet sheet = workbook.getSheetAt(0);
                AtomicInteger ai = new AtomicInteger();
                sheet.forEach(row -> {
                    int i = ai.incrementAndGet();
                    AtomicInteger aj = new AtomicInteger();
                    if (i == 1) {//首行  提取注解
                        row.forEach(cell -> {
                            int j = aj.incrementAndGet();
                            String cellValue = getCellValue(cell);
                            if (classMap.containsKey(cellValue)) {
                                reflectionMap.put(j, classMap.get(cellValue));
                            }
                        });
                    } else {
                        try {
                            T t = cls.newInstance();
                            row.forEach(cell -> {
                                int j = aj.incrementAndGet();

                                if (reflectionMap.containsKey(j)) {
                                    Field thisField = reflectionMap.get(j).get(0);
                                    RequiredColumn requiredColumn = thisField.getAnnotation(RequiredColumn.class);
                                    String cellValue = getCellValue(cell);
                                    if (requiredColumn != null) {
                                        if (cellValue.equals("")) {
                                            flag.set(false);
                                        }
                                    }
                                    List<Field> fieldList = reflectionMap.get(j);
                                    for (Field field : fieldList) {
                                        try {
                                            field.set(t, cellValue);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            });
                            dataList.add(t);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        if (flag.get()) {
            return dataList;
        } else {
            return null;
        }

    }

    /**
     * 获取excel 单元格数据
     *
     * @param cell
     * @return
     */
    private static String getCellValue(Cell cell) {
        if (cell.getCellType() == cell.CELL_TYPE_BOOLEAN) {
            return String.valueOf(cell.getBooleanCellValue()).trim();
        } else if (cell.getCellType() == cell.CELL_TYPE_NUMERIC) {
            return customExcelNumericFormat(cell.getNumericCellValue()).trim();
        } else {
            return String.valueOf(cell.getStringCellValue()).trim();
        }
    }

    // 对纯数字和小数点的单元格进行处理（防止java读入double自动补.0和自动读成科学计数法）
    private static String customExcelNumericFormat(double d) {
        NumberFormat nf = NumberFormat.getNumberInstance();
        String temp = d + "";
        // 科学计数法中的n（10的n次方）
        int n = 0;
        // 判断有多少位有效小数
        int a = 0;
        // 如果该数字使用了科学计数法
        if (temp.indexOf("E") >= 0) {
            // 判断出要移多少位
            String auxiliaryStr = temp.split("E")[1];
            String realStr = temp.split("E")[0];
            n = Integer.parseInt(auxiliaryStr);
            // 有多少位有效小数（科学计数法）
            a = (realStr).length() - (realStr).indexOf(".") - 1 - n;
        } else {
            // 有多少位有效小数（非科学计数法）
            a = (d + "").length() - (d + "").indexOf(".") - 1;
        }
        if (a == 1 && (d + "").endsWith(".0")) {
            // 如果excel里本无小数是java读取时自动加的.0那就直接将小数位数设0
            nf.setMinimumFractionDigits(0);
        } else {
            // 有小数的按小数位数设置
            nf.setMinimumFractionDigits(a);
        }
        String s = nf.format(d);
        if (s.indexOf(",") >= 0) {
            s = s.replace(",", "");
        }
        return s;
    }
}
