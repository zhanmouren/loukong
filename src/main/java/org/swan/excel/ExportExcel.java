package org.swan.excel;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.swan.excel.Excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;

public class ExportExcel {
	
	public static final HttpEntity<?> export(String fileName, String templatePath, String btlFile, Map<String, Object> map) {
		 boolean isRead =false;
		 HttpEntity<?> entity = export(isRead, fileName, templatePath, btlFile, map);
	     return entity;
	}

    public static final HttpEntity<?> export(boolean isRead,String fileName, String templatePath, String btlFile, Map<String, Object> map) {
        InputStream in = null;
        String name = null;
        try {
            name = new String(fileName.getBytes("utf8"),"ISO8859-1");
            in = Resource.getResourceInputStream(templatePath);
            if (in != null) {
                return Excel.createFile(isRead,fileName, in, btlFile, map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        HttpHeaders header = new HttpHeaders();
        header.add("Content-Disposition", "attachment ; filename=" + name + ".xlsx");
        header.add("Content-Type", "application/octet-stream");
        ResponseEntity<byte[]> entity = new ResponseEntity<byte[]>("".getBytes(),header, HttpStatus.OK);
        return entity;
    }
    /**
     * @desc 服务器文件模板
     * @author dulingjiang
     * @date 2019年8月21日
     * @param isRead
     * @param fileName
     * @param templatePath
     * @param btlFile
     * @param map
     * @return
     */
    public static final HttpEntity<?> exportFile(String fileName, String templatePath, String btlFile, Map<String, Object> map) throws Exception{
    	File file = new File(templatePath);
    	InputStream in = null;String name = null;
        try {
            name = new String(fileName.getBytes("utf8"),"ISO8859-1");
            in = new FileInputStream(file);
            if (in != null) {
                return Excel.createFile(false,fileName, in, btlFile, map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                in.close();
            }
        }
        HttpHeaders header = new HttpHeaders();
        header.add("Content-Disposition", "attachment ; filename=" + name + ".xlsx");
        header.add("Content-Type", "application/octet-stream");
        ResponseEntity<byte[]> entity = new ResponseEntity<byte[]>("".getBytes(),header, HttpStatus.OK);
        return entity;
    }
}
