package com.koron.inwlms.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * 深圳科荣软件有限公司
 *
 * @ClassName GZipUtils
 * @Description
 * @Author hongxl
 * @Date 2019/9/23 14:18
 */
public class GZipUtils {
    /**
     * 压缩
     * @param data
     * @return
     * @throws IOException
     */
    public static byte[] compress(byte[] data) throws IOException {
        if (data == null || data.length == 0) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip = new GZIPOutputStream(out);
        gzip.write(data);
        gzip.close();
        return  out.toByteArray();//out.toString("ISO-8859-1");
    }

    /**
     * 压缩 参数string
     * @param str
     * @return
     * @throws IOException
     */
    public static byte[] compress(String str) throws IOException {
        if (str == null || str.length() == 0) {
            return null;
        }
        return compress(str.getBytes("utf-8"));
    }

    /**
     * 解压缩
     * @param data
     * @return
     * @throws IOException
     */
    public static byte[] uncompress(byte[] data) throws IOException {
        if (data == null || data.length == 0) {
            return data;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        GZIPInputStream gunzip = new GZIPInputStream(in);
        byte[] buffer = new byte[256];
        int n;
        while ((n = gunzip.read(buffer)) >= 0) {
            out.write(buffer, 0, n);
        }
        gunzip.close();
        in.close();
        return out.toByteArray();
    }

    /**
     * 解压缩 参数string
     * @param str
     * @return
     * @throws IOException
     */
    public static String uncompress(String str) throws IOException {
        if (str == null || str.length() == 0) {
            return str;
        }
        byte[] data = uncompress(str.getBytes("utf-8")); // ISO-8859-1
        return new String(data);
    }

    /**
     * 指定读取文件，需要从压缩文件中读取文件内容的文件名
     * @param unZipfile
     * @param destFile
     * @return
     */
    public static String unZip(String unZipfile, String destFile) {// unZipfileName需要解压的zip文件名
        InputStream inputStream;
        String inData = null;
        try {
            // 生成一个zip的文件
            File f = new File(unZipfile);
            ZipFile zipFile = new ZipFile(f);

            // 遍历zipFile中所有的实体，并把他们解压出来
            ZipEntry entry = zipFile.getEntry(destFile);
            if (!entry.isDirectory()) {
                // 获取出该压缩实体的输入流
                inputStream = zipFile.getInputStream(entry);

                ByteArrayOutputStream out = new ByteArrayOutputStream();
                byte[] bys = new byte[4096];
                for (int p = -1; (p = inputStream.read(bys)) != -1;) {
                    out.write(bys, 0, p);
                }
                inData = out.toString();
                out.close();
                inputStream.close();
            }
            zipFile.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return inData;
    }
}
